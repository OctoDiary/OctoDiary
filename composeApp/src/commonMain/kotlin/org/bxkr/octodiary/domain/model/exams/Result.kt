package org.bxkr.octodiary.domain.model.exams

import kotlinx.serialization.Serializable

@Serializable
sealed class Result {
    @Serializable
    data class ScoreResult(
        val score: Int
    ) : Result()

    @Serializable
    data class MarkResult(
        val mark: Int
    ) : Result()

    @Serializable
    data class BoolResult(
        val boolResult: Boolean
    ) : Result()
}