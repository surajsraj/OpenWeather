package com.android.openweather.network.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Weather (
    var id: Int? = null,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null
)
