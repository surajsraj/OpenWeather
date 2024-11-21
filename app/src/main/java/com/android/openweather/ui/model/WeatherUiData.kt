package com.android.openweather.ui.model

import com.android.openweather.network.model.weather.Weather
import com.android.openweather.network.model.weather.WeatherData

data class WeatherUiData(
    var name: String,
    var weather: List<Weather>,
    var temp: String
)

fun WeatherData.toWeatherUiData(): WeatherUiData = WeatherUiData(
    name = name ?: "",
    weather = weather ?: emptyList(),
    temp = main?.temp.toString()
)

fun WeatherUiData.getIconUrl(): String =
    if (weather.isNotEmpty()) "$ICON_BASE_URL${weather[0].icon}.png" else ""

private const val ICON_BASE_URL = "https://openweathermap.org/img/w/"