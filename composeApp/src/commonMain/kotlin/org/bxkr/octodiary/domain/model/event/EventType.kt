package org.bxkr.octodiary.domain.model.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EventType {
    @SerialName("main_plan")
    MainPlan, // занятия, уроки, пары в соответствии с расписанием

    @SerialName("additional")
    Additional, // "внеурочная деятельность"

    @SerialName("personal")
    Personal
}