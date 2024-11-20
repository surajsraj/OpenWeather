package com.android.openweather.ui.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.openweather.di.IODispatcher
import com.android.openweather.di.MainDispatcher
import com.android.openweather.model.geolocation.CityUiData
import com.android.openweather.model.util.toCityUiData
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
class CityViewModel @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiStateOfCityList: MutableStateFlow<UiState<List<CityUiData>>> =
        MutableStateFlow(UiState.Initial)
    val uiStateOfCityList: StateFlow<UiState<List<CityUiData>>> = _uiStateOfCityList.asStateFlow()

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun getCityData(city: String) {
        viewModelScope.launch(ioDispatcher) {
            networkDataSource.getCitiesData(city).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Fetching -> {
                        withContext(mainDispatcher) {
                            _uiStateOfCityList.update {
                                UiState.Loading
                            }
                        }
                    }

                    is NetworkResult.Success -> {
                        withContext(mainDispatcher) {
                            val cityUiData = networkResult.data.map {
                                it.toCityUiData()
                            }
                            _uiStateOfCityList.update {
                                UiState.Success(cityUiData)
                            }
                        }
                    }

                    is NetworkResult.Error -> {
                        withContext(mainDispatcher) {
                            _uiStateOfCityList.update {
                                UiState.Failure
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
        _uiStateOfCityList.update { UiState.Initial }
    }
}
