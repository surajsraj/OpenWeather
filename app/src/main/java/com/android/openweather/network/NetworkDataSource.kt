package com.android.openweather.network

import com.android.openweather.model.geolocation.CityData
import com.android.openweather.model.weather.WeatherData

interface NetworkDataSource {
    suspend fun getCitiesData(city: String, limit: Int): List<CityData>
    suspend fun getWeatherForCoordinates(lat: Double, lon: Double): WeatherData
}