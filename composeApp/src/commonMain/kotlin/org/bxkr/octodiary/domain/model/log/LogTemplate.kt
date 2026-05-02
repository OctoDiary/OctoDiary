package org.bxkr.octodiary.domain.model.log

import org.bxkr.octodiary.domain.exception.diary.UnknownDiaryException

class LogTemplate private constructor(
    val text: String,
    val level: LogLevel
) {
    companion object {
        fun unknownDiaryException(exception: UnknownDiaryException) = LogTemplate(
            "Unknown diary exception" +
                    "| - Source: ${exception.source}".trimMargin(), LogLevel.ERROR
        )

        fun successfullyLoaded(loadedPartName: String, source: String) =
            LogTemplate("$source successfully loaded $loadedPartName", LogLevel.INFO)
    }
}