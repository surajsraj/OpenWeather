package com.android.openweather.network.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Main (
    var temp: Double? = null,
    var feelsLike: Double? = null,
    var tempMin: Double? = null,
    var tempMax: Double? = null,
    var pressure: Int? = null,
    var humidity: Int? = null,
    var seaLevel: Int? = null,
    var grndLevel: Int? = null
)
