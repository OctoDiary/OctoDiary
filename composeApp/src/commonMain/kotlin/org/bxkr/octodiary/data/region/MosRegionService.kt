package org.bxkr.octodiary.data.region

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.continue_in_telegram
import kmpdiary.composeapp.generated.resources.log_in
import kmpdiary.composeapp.generated.resources.mosru_login
import kmpdiary.composeapp.generated.resources.mosru_login_desc
import kmpdiary.composeapp.generated.resources.mosru_login_suffix
import kmpdiary.composeapp.generated.resources.mosru_wv_login
import kmpdiary.composeapp.generated.resources.mosru_wv_login_desc
import kmpdiary.composeapp.generated.resources.mosru_wv_login_desc_ios
import kmpdiary.composeapp.generated.resources.telegram_login
import kmpdiary.composeapp.generated.resources.telegram_login_desc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.DeeplinkHolder
import org.bxkr.octodiary.IOSPlatform
import org.bxkr.octodiary.Region
import org.bxkr.octodiary.data.ExternalIntegration
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.data.auth.login.MosRuService
import org.bxkr.octodiary.ui.components.ErrorDialog
import org.bxkr.octodiary.ui.platform
import org.bxkr.octodiary.ui.resolve
import org.bxkr.octodiary.ui.screens.auth.CommonFooter
import org.bxkr.octodiary.ui.screens.auth.FooterAdditionalIcon
import org.bxkr.octodiary.ui.screens.auth.FooterInput
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel
import org.jetbrains.compose.resources.StringResource
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MosRegionService : RegionService, KoinComponent {
    private val mosRuService: MosRuService by inject()

    private data class ButtonConfig(
        val text: StringResource,
        val icon: ImageVector = Icons.AutoMirrored.Rounded.OpenInNew,
        val onClick: suspend (UriHandler, AuthViewModel) -> Unit
    )

    private enum class LoginVariant {
        MosRu,
        MosRuWebView,
        Telegram
    }

    private val telegramLink = ExternalIntegration.getTelegramAuthLink(Region.Moscow.regionCode)

    private val isLoading = MutableStateFlow(false)

    private val error = MutableStateFlow<Result.Error?>(null)

    private val mosRuConfig = ButtonConfig(Res.string.log_in) { uriHandler, _ ->
        isLoading.value = true
        val result = mosRuService.startAuthInBrowser(uriHandler)
        isLoading.value = false
        if (result is Result.Error) {
            error.value = result
        }
    }

    private val mosRuWebViewConfig =
        ButtonConfig(Res.string.log_in, Icons.AutoMirrored.Rounded.ArrowForward) { _, vm ->
            vm.goNext() // goes to Step3() (see in this file)
        }

    private val telegramConfig = ButtonConfig(Res.string.continue_in_telegram) { uriHandler, _ ->
        uriHandler.openUri(telegramLink)
    }

    @Composable
    override fun Step2(
        vm: AuthViewModel
    ) {
        val uriHandler = LocalUriHandler.current
        val clipboardManager = LocalClipboardManager.current
        val errorState by error.collectAsState()

        val availableMethods = when (platform()) {
            is IOSPlatform -> listOf(LoginVariant.MosRuWebView, LoginVariant.Telegram)
            else -> LoginVariant.entries
        }

        val recommendedMethod = when (platform()) {
            is IOSPlatform -> LoginVariant.MosRuWebView
            else -> LoginVariant.MosRu
        }

        var choice by remember { mutableStateOf(recommendedMethod) }
        val additionalIcon =
            if (choice == LoginVariant.Telegram) FooterAdditionalIcon(Icons.Rounded.ContentCopy) {
                clipboardManager.setText(AnnotatedString(telegramLink))
            } else null
        val finalButtonConfig: ButtonConfig = when (choice) {
            LoginVariant.MosRu -> mosRuConfig
            LoginVariant.MosRuWebView -> mosRuWebViewConfig
            LoginVariant.Telegram -> telegramConfig
        }

        if (errorState != null) {
            val error1 = errorState
            if (error1 is Result.Error) {
                ErrorDialog(error1) {
                    error.value = null
                }
            }
        }

        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedCard(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(Modifier.selectableGroup()) {
                        availableMethods.forEachIndexed { index, it ->
                            RadioItem(it, choice == it, recommendedMethod == it) { choice = it }
                            if (index < LoginVariant.entries.lastIndex) HorizontalDivider()
                        }
                    }
                }
            }
            CommonFooter( // common Step2 footer with back and forward buttons
                vm,
                finalButtonConfig.run {
                    FooterInput(
                        text,
                        icon,
                        additionalIcon,
                        isLoading.collectAsState().value
                    ) { vm.viewModelScope.launch { onClick(uriHandler, vm) } }
                }
            )
        }
    }

    @Composable
    override fun Step3(vm: AuthViewModel) {
        val deeplink by koinInject<DeeplinkHolder>().deeplink.collectAsState()
        if (vm.currentPage.collectAsState().value == 2) {
            val deeplink1 = deeplink
            if (deeplink1 == null)
                mosRuService.AuthWebView(vm.needToRefresh.collectAsState().value)
            else Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                mosRuService.AuthCode(deeplink1)
            }
        }
    }

    @Composable
    private fun RadioItem(
        id: LoginVariant,
        selected: Boolean,
        isRecommended: Boolean,
        onSelect: () -> Unit
    ) {
        Column(
            Modifier
                .selectable(
                    selected, role = Role.RadioButton
                ) { onSelect() }.padding(16.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected, null)
                Text(
                    when (id) {
                        LoginVariant.MosRu -> Res.string.mosru_login
                        LoginVariant.MosRuWebView -> if (platform() is IOSPlatform) Res.string.mosru_login else Res.string.mosru_wv_login
                        LoginVariant.Telegram -> Res.string.telegram_login
                    }.resolve(),
                    Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                if (isRecommended)
                Text(
                    ' ' + Res.string.mosru_login_suffix.resolve(),
                    Modifier.alpha(.5f),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(Modifier.size(8.dp))
            Text(
                when (id) {
                    LoginVariant.MosRu -> Res.string.mosru_login_desc
                    LoginVariant.MosRuWebView -> if (platform() is IOSPlatform) Res.string.mosru_wv_login_desc_ios else Res.string.mosru_wv_login_desc
                    LoginVariant.Telegram -> Res.string.telegram_login_desc
                }.resolve(),
                Modifier.alpha(.8f),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}