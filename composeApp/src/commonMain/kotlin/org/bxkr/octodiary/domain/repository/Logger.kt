package org.bxkr.octodiary.domain.repository

import org.bxkr.octodiary.domain.model.log.LogLevel
import org.bxkr.octodiary.domain.model.log.LogTemplate

interface Logger {
    fun log(text: String, logLevel: LogLevel = LogLevel.INFO)
    fun log(logTemplate: LogTemplate)
}