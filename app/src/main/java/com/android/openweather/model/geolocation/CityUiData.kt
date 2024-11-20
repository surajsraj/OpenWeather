package com.android.openweather.model.geolocation

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