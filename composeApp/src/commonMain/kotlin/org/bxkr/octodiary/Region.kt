package org.bxkr.octodiary

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
import org.bxkr.octodiary.data.region.MosRegionService
import org.bxkr.octodiary.data.region.MosregRegionService
import org.bxkr.octodiary.data.region.RegionService
import org.jetbrains.compose.resources.StringResource

enum class Region(val regionCode: String, val isCapital: Boolean = false) {
    Moscow("77", isCapital = true),
    Suburb("50"),
    Kaluga("40"),
    Tatarstan("16"),
    Dagestan("05"),
    Tumen("72"),
    Chechnya("95"),
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

val Region.service: RegionService
    get() = when (this) {
        Region.Moscow -> MosRegionService()
        Region.Suburb -> MosregRegionService()
        else -> throw NotImplementedError("This region is not yet implemented.")
    }