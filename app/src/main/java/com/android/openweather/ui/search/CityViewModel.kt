package com.android.openweather.ui.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.openweather.network.NetworkDataSource
import com.android.openweather.network.util.NetworkResult
import com.android.openweather.ui.model.CityUiData
import com.android.openweather.ui.model.toCityUiData
import com.android.openweather.ui.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : ViewModel() {

    private val _uiStateOfCityList: MutableStateFlow<UiState<List<CityUiData>>> =
        MutableStateFlow(UiState.Initial)
    val uiStateOfCityList: StateFlow<UiState<List<CityUiData>>> = _uiStateOfCityList.asStateFlow()

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun getCityData(city: String) {
        viewModelScope.launch {
            networkDataSource.getCitiesData(city).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Fetching -> {
                        _uiStateOfCityList.update {
                            UiState.Loading
                        }
                    }

                    is NetworkResult.Success -> {
                        val cityUiData = networkResult.data.map {
                            it.toCityUiData()
                        }
                        _uiStateOfCityList.update {
                            UiState.Success(cityUiData)
                        }
                    }

                    is NetworkResult.Error -> {
                        _uiStateOfCityList.update {
                            UiState.Failure
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
