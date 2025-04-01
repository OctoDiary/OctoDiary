package org.bxkr.octodiary.network.apis

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