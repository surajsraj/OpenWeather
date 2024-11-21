package com.android.openweather.network

import com.android.openweather.network.model.geolocation.CityData
import com.android.openweather.network.model.weather.WeatherData
import com.android.openweather.network.constants.CITY_RESULTS_LIMIT
import com.android.openweather.network.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun getCitiesData(city: String, limit: Int = CITY_RESULTS_LIMIT): Flow<NetworkResult<List<CityData>>>
    suspend fun getWeatherForCoordinates(lat: Double, lon: Double): Flow<NetworkResult<WeatherData>>
}