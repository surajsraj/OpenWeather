package com.android.openweather.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Wind (
    var speed: Double,
    var deg: Int,
    var gust: Double
)
