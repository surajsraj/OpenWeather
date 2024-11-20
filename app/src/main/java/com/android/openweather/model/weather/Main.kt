package com.android.openweather.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Main (
    var temp: Double,
    var feelsLike: Double,
    var tempMin: Double,
    var tempMax: Double,
    var pressure: Int,
    var humidity: Int,
    var seaLevel: Int,
    var grndLevel: Int
)
