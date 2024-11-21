package com.android.openweather.network.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Sys (
    var type: Int? = null,
    var id: Int? = null,
    var country: String? = null,
    var sunrise: Int? = null,
    var sunset: Int? = null
)
