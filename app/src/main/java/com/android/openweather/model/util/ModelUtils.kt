package com.android.openweather.model.util

import com.android.openweather.model.geolocation.CityData
import com.android.openweather.model.geolocation.CityUiData
import com.android.openweather.model.weather.WeatherData
import com.android.openweather.model.weather.WeatherUiData

fun WeatherData.toWeatherUiData(): WeatherUiData = WeatherUiData(
    name = name ?: "",
    weather = weather ?: emptyList(),
    temp = main?.temp.toString()
)

fun CityData.toCityUiData(): CityUiData = CityUiData(
    name = name,
    latitude = lat,
    longitude = lon
)

fun WeatherUiData.getIconUrl(): String =
    if (weather.isNotEmpty()) "$ICON_BASE_URL${weather[0].icon}.png" else ""

private const val ICON_BASE_URL = "https://openweathermap.org/img/w/"