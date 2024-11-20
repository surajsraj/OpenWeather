package com.android.openweather.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.openweather.network.NetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : ViewModel() {


    fun getCityData(city: String) {
        viewModelScope.launch {
            networkDataSource.getCitiesData(city)
        }
    }
}
