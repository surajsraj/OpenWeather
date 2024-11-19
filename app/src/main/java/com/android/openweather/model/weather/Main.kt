package com.android.openweather.model.weather

import kotlinx.serialization.Serializable

@Serializable
class Main {
    var temp: Double? = null
    var feelsLike: Double? = null
    var tempMin: Double? = null
    var tempMax: Double? = null
    var pressure: Int? = null
    var humidity: Int? = null
    var seaLevel: Int? = null
    var grndLevel: Int? = null
}
