package org.bxkr.octodiary.domain

object ExternalIntegration {
    const val TELEGRAM_REPORT_URL = "https://t.me/OctoDiaryBot?start=feedback"
    const val TELEGRAM_NEW_REGION_LINK = "https://t.me/m/cwSmnTiXZWNi"
    const val TELEGRAM_NEW_DIARY_LINK = "https://t.me/m/lgHCP6UHNmZi"
    fun getTelegramAuthLink(regionCode: String) =
        "https://t.me/OctoDiaryBot/?start=app_auth_$regionCode"
}