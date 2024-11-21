package com.android.openweather.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.openweather.di.IODispatcher
import com.android.openweather.di.MainDispatcher
import com.android.openweather.ui.model.toWeatherUiData
import com.android.openweather.ui.model.WeatherUiData
import com.android.openweather.network.NetworkDataSource
import com.android.openweather.network.util.NetworkResult
import com.android.openweather.ui.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<WeatherUiData>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<WeatherUiData>> = _uiState.asStateFlow()

    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch(ioDispatcher) {
            networkDataSource.getWeatherForCoordinates(latitude, longitude).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Fetching -> {
                        withContext(mainDispatcher) {
                            _uiState.update { UiState.Loading }
                        }
                    }

                    is NetworkResult.Success -> {
                        withContext(mainDispatcher) {
                            _uiState.update { UiState.Success(networkResult.data.toWeatherUiData()) }
                        }
                    }

                    is NetworkResult.Error -> {
                        withContext(mainDispatcher) {
                            _uiState.update { UiState.Failure }
                        }
                    }
                }
            }
        }
    }
}
