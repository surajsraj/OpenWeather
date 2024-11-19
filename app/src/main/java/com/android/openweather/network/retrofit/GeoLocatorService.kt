package com.android.openweather.network.retrofit

import com.android.openweather.model.geolocation.CityData
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoLocatorService {
    @GET("geo/1.0/direct")
    suspend fun getCities(
        @Query("q") city: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): List<CityData>
}