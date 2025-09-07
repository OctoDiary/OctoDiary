package org.bxkr.octodiary.network

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.request.header
import io.ktor.http.HttpMessageBuilder
import kotlinx.io.IOException
import org.bxkr.octodiary.network.exception.WrongResponseTypeException

fun HttpMessageBuilder.authHeader(headerValue: String) = header("Authorization", headerValue)

fun IOException.getMessage(additionalInfo: String) = "${this::class.simpleName}: Failed to execute ${additionalInfo}.\nMessage: $message.\nStack trace: ${stackTraceToString()}"
fun NoTransformationFoundException.getMessage(additionalInfo: String) = "NoTransformationFoundException: Invalid response of ${additionalInfo}.\nMessage: $message.\nStack trace: ${stackTraceToString()}"
fun getWrongResponseTypeException(additionalInfo: String) = WrongResponseTypeException("WrongResponseTypeException: Invalid response of ${additionalInfo}.")
