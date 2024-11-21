package com.android.openweather.network.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Clouds (
    var all: Int? = null
)
