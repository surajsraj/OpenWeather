package com.android.openweather.network.retrofit

import com.android.openweather.model.weather.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServiceApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): WeatherData
}