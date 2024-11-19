package com.android.openweather.model.geolocation


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityData(
    @SerialName("country")
    val country: String = "",
    @SerialName("lat")
    val lat: Double = 0.0,
    @SerialName("local_names")
    val localNames: LocalNames? = null,
    @SerialName("lon")
    val lon: Double = 0.0,
    @SerialName("name")
    val name: String = "",
    @SerialName("state")
    val state: String = ""
)