package com.android.openweather.network.retrofit

import com.android.openweather.network.model.geolocation.CityData
import com.android.openweather.network.model.weather.WeatherData
import com.android.openweather.network.NetworkDataSource
import com.android.openweather.network.constants.API_KEY
import com.android.openweather.network.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class RetrofitNetworkDataSource @Inject constructor(
    private val weatherApi: WeatherServiceApi,
    private val geoLocatorApi: GeoLocatorServiceApi
) : NetworkDataSource {

    override fun getCitiesData(
        city: String,
        limit: Int
    ): Flow<NetworkResult<List<CityData>>> = flow {
        emit(NetworkResult.Fetching)
        try {
            val result = geoLocatorApi.getCities(city, limit, API_KEY)
            emit(NetworkResult.Success(result))
        } catch (exception: HttpException) {
            emit(NetworkResult.Error(exception.message.orEmpty()))
        }
    }

    override fun getWeatherForCoordinates(
        lat: Double,
        lon: Double
    ): Flow<NetworkResult<WeatherData>> = flow {
        emit(NetworkResult.Fetching)
        try {
            val result = weatherApi.getWeather(lat, lon, API_KEY)
            emit(NetworkResult.Success(result))
        } catch (exception: HttpException) {
            emit(NetworkResult.Error(exception.message.orEmpty()))
        }
    }
}