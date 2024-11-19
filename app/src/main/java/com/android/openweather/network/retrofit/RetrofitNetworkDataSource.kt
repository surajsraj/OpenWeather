package com.android.openweather.network.retrofit

import com.android.openweather.model.geolocation.CityData
import com.android.openweather.model.weather.WeatherData
import com.android.openweather.network.NetworkDataSource
import com.android.openweather.network.constants.API_KEY
import com.android.openweather.network.constants.BASE_URL
import okhttp3.MediaType.Companion.toMediaType
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


internal class RetrofitNetworkDataSource constructor(): NetworkDataSource {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val weatherApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(WeatherServiceApi::class.java)

    private val geoLocatorApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(GeoLocatorService::class.java)

    override suspend fun getCitiesData(city: String, limit: Int): List<CityData> {
        return geoLocatorApi.getCities(city, limit, API_KEY)
    }

    override suspend fun getWeatherForCoordinates(lat: Double, lon:Double): WeatherData {
        return weatherApi.getWeather(lat, lon, API_KEY)
    }

}