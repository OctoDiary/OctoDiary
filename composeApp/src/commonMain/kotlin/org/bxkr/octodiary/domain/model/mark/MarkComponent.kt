package org.bxkr.octodiary.domain.model.mark

import kotlinx.serialization.Serializable

@Serializable
sealed class MarkComponent {
    @Serializable
    class IntegerMark(
        val value: Int,
        val weight: Int = 1,
        val isCounted: Boolean = true, // is counted when counting average mark
    ) : MarkComponent()

    @Serializable
    class BoolMark(
        val value: Boolean,
        val label: String // 1-3 characters, e.g. "Зч" (зачет), "Н/А" (неаттестация)
    ) : MarkComponent()

    @Serializable
    class StringMark(
        val label: String // 1-3 characters, e.g. "См"
    ) : MarkComponent()
}