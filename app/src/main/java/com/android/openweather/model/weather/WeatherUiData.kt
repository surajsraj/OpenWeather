package com.android.openweather.model.weather

data class WeatherUiData(
    var name: String,
    var weather: List<Weather>,
    var main: Main
)