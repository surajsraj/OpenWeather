package com.android.openweather.ui.model

import com.android.openweather.network.model.geolocation.CityData

data class CityUiData(
    var name: String,
    var latitude: Double,
    var longitude: Double
)

fun CityData.toCityUiData(): CityUiData = CityUiData(
    name = name,
    latitude = lat,
    longitude = lon
)