package org.bxkr.octodiary.data.repository.diary

import org.bxkr.octodiary.domain.model.log.LogLevel
import org.bxkr.octodiary.domain.model.log.LogTemplate
import org.bxkr.octodiary.domain.repository.Logger
import org.koin.core.annotation.Single

@Single
class LoggerImpl : Logger {
    override fun log(text: String, logLevel: LogLevel) {
        // Other logging implementations can be added here
        println("Logger [${logLevel.name.first()}]: $text")
    }

    override fun log(logTemplate: LogTemplate) = logTemplate.run { log(text, level) }
}