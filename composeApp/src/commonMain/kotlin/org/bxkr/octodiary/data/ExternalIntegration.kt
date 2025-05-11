package org.bxkr.octodiary.data

object ExternalIntegration {
    const val TELEGRAM_REPORT_URL = "https://t.me/OctoDiaryBot?start=feedback"
    const val TELEGRAM_NEW_REGION_LINK = "https://t.me/m/cwSmnTiXZWNi"
    fun getTelegramAuthLink(regionCode: String) =
        "https://t.me/OctoDiaryBot/?start=app_auth_$regionCode"
}