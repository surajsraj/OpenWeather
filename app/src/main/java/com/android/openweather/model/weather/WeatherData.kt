package com.android.openweather.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class WeatherData (
    var coord: Coord,
    var weather: List<Weather>,
    var base: String,
    var main: Main,
    var visibility: Int,
    var wind: Wind,
    var rain: Rain,
    var clouds: Clouds,
    var dt: Int,
    var sys: Sys,
    var timezone: Int,
    var id: Int,
    var name: String,
    var cod: Int
)
