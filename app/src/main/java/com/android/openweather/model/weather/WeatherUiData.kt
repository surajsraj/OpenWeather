package com.android.openweather.model.weather

data class WeatherUiData(
    var name: String,
    var weather: List<Weather>,
    var temp: String
)