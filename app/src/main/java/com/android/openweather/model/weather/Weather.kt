package com.android.openweather.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Weather (
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)
