package org.bxkr.octodiary

import androidx.compose.runtime.Composable
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.chechnya
import kmpdiary.composeapp.generated.resources.dagestan
import kmpdiary.composeapp.generated.resources.kaluga
import kmpdiary.composeapp.generated.resources.mes
import kmpdiary.composeapp.generated.resources.moscow
import kmpdiary.composeapp.generated.resources.mosreg
import kmpdiary.composeapp.generated.resources.myschool
import kmpdiary.composeapp.generated.resources.tatarstan
import kmpdiary.composeapp.generated.resources.tumen
import kmpdiary.composeapp.generated.resources.yamal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bxkr.octodiary.data.region.MosRegionService
import org.bxkr.octodiary.data.region.MosregRegionService
import org.bxkr.octodiary.data.region.RegionService
import org.jetbrains.compose.resources.StringResource
import org.koin.compose.koinInject

@Serializable
enum class Region(
    val regionCode: String
) {
    @SerialName("77")
    Moscow("77"),

    @SerialName("50")
    Suburb("50"),

    @SerialName("40")
    Kaluga("40"),

    @SerialName("16")
    Tatarstan("16"),

    @SerialName("05")
    Dagestan("05"),

    @SerialName("72")
    Tumen("72"),

    @SerialName("95")
    Chechnya("95"),

    @SerialName("89")
    Yamal("89")
}

val Region.nameResource: StringResource
    get() = when (this) {
        Region.Moscow -> Res.string.moscow
        Region.Suburb -> Res.string.mosreg
        Region.Kaluga -> Res.string.kaluga
        Region.Tatarstan -> Res.string.tatarstan
        Region.Dagestan -> Res.string.dagestan
        Region.Tumen -> Res.string.tumen
        Region.Chechnya -> Res.string.chechnya
        Region.Yamal -> Res.string.yamal
    }

val Region.diaryName: List<StringResource>
    get() = when (this) {
        Region.Moscow -> listOf(Res.string.mes)
        else -> listOf(Res.string.myschool)
    }

val implementedRegions = arrayOf(
    Region.Moscow,
    Region.Suburb,
//    Region.Kaluga,
//    Region.Tatarstan,
//    Region.Dagestan,
//    Region.Tumen,
//    Region.Chechnya,
//    Region.Yamal
)

@Composable
fun Region.getService(): RegionService = when (this) {
    Region.Moscow -> koinInject<MosRegionService>()
    Region.Suburb -> koinInject<MosregRegionService>()
    else -> throw NotImplementedError("This region is not yet implemented.")
}