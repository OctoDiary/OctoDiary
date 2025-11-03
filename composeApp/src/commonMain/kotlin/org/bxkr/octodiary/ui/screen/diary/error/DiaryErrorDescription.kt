package org.bxkr.octodiary.ui.screen.diary.error

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import octodiary4.composeapp.generated.resources.Res
import octodiary4.composeapp.generated.resources.access_credentials_not_found
import octodiary4.composeapp.generated.resources.expired_access_credentials
import octodiary4.composeapp.generated.resources.session_not_found
import octodiary4.composeapp.generated.resources.unknown_diary_error
import octodiary4.composeapp.generated.resources.unsupported_feature_error
import org.bxkr.octodiary.domain.exception.diary.DiaryException
import org.bxkr.octodiary.domain.exception.diary.NotAuthorizedException
import org.bxkr.octodiary.domain.exception.diary.NotAuthorizedType
import org.bxkr.octodiary.domain.exception.diary.UnknownDiaryException
import org.bxkr.octodiary.domain.exception.diary.UnsupportedFeatureException
import org.jetbrains.compose.resources.stringResource

@Composable
fun DiaryErrorDescription(exception: DiaryException) {
    Text(exceptionSwitch(exception))
}

@Composable
private fun exceptionSwitch(exception: DiaryException) = when (exception) {
    is NotAuthorizedException -> notAuthorizedSwitch(exception)
    is UnsupportedFeatureException -> stringResource(Res.string.unsupported_feature_error)
    is UnknownDiaryException -> stringResource(Res.string.unknown_diary_error)
}

@Composable
private fun notAuthorizedSwitch(exception: NotAuthorizedException) =
    when (exception.notAuthorizedType) {
        NotAuthorizedType.AccessCredentialsNotFound -> stringResource(Res.string.access_credentials_not_found)
        NotAuthorizedType.ExpiredAccessCredentials -> stringResource(Res.string.expired_access_credentials)
        NotAuthorizedType.SessionNotFound -> stringResource(Res.string.session_not_found)
    }