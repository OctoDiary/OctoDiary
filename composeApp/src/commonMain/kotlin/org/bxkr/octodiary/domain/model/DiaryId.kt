package org.bxkr.octodiary.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DiaryId {
    @SerialName("moscow_mes") MesMos,
    @SerialName("moscow_mes_spo") SpoMos,
    @SerialName("mosreg_myschool") MesMosReg,
    @SerialName("kaluga_myschool") MesKaluga,
}
