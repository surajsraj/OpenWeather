package com.android.openweather.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.openweather.network.NetworkDataSource
import com.android.openweather.network.util.NetworkResult
import com.android.openweather.ui.model.WeatherUiData
import com.android.openweather.ui.model.toWeatherUiData
import com.android.openweather.ui.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<WeatherUiData>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<WeatherUiData>> = _uiState.asStateFlow()

    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            networkDataSource.getWeatherForCoordinates(latitude, longitude)
                .collect { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Fetching -> {
                            _uiState.update { UiState.Loading }
                        }

                        is NetworkResult.Success -> {
                            _uiState.update { UiState.Success(networkResult.data.toWeatherUiData()) }
                        }

                        is NetworkResult.Error -> {
                            _uiState.update { UiState.Failure }
                        }
                    }
                }
        }
    }
}
