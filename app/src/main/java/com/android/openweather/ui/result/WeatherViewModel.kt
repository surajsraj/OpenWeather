package com.android.openweather.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.openweather.network.NetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : ViewModel() {

    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            networkDataSource.getWeatherForCoordinates(latitude, longitude)
        }
    }
}
