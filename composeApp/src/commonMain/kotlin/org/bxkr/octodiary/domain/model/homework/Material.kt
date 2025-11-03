package org.bxkr.octodiary.domain.model.homework

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Material {
    @Serializable
    data class AttachedFile(
        val link: String,
        val name: String? = null
    ) : Material()

    @Serializable
    data class Interactive(
        val materialType: String,
        val materialData: Map<String, String>,
        val name: String? = null,
        val icon: Icon = Icon.Unknown,
        val mode: Mode = Mode.Execute
    ) : Material() {
        @Serializable
        enum class Icon {
            @SerialName("test")
            Test,

            @SerialName("app")
            App,

            @SerialName("game")
            Game,

            @SerialName("book")
            Book,

            @SerialName("unknown")
            Unknown,
        }

        @Serializable
        enum class Mode {
            @SerialName("execute")
            Execute,

            @SerialName("learn")
            Learn
        }
    }
}