package com.android.openweather.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Sys (
    var type: Int,
    var id: Int,
    var country: String,
    var sunrise: Int,
    var sunset: Int
)
