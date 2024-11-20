package com.android.openweather.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    var lon: Double,
    var lat: Double
)
