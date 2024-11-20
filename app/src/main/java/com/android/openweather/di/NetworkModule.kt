package com.android.openweather.di

import com.android.openweather.network.NetworkDataSource
import com.android.openweather.network.constants.BASE_URL
import com.android.openweather.network.retrofit.GeoLocatorServiceApi
import com.android.openweather.network.retrofit.RetrofitNetworkDataSource
import com.android.openweather.network.retrofit.WeatherServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    @Singleton
    @Provides
    fun provideRetrofit(json: Json): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()),
        )
        .build()

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherServiceApi = retrofit
        .create(WeatherServiceApi::class.java)

    @Singleton
    @Provides
    fun provideGeoLocatorApi(retrofit: Retrofit): GeoLocatorServiceApi = retrofit
        .create(GeoLocatorServiceApi::class.java)

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        weatherServiceApi: WeatherServiceApi,
        geoLocatorServiceApi: GeoLocatorServiceApi
    ): NetworkDataSource = RetrofitNetworkDataSource(weatherServiceApi, geoLocatorServiceApi)
}