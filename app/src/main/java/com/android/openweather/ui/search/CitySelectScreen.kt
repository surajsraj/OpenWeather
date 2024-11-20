package com.android.openweather.ui.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CityDataRoute(
    onCitySelected: (latitude: Double, longitude: Double) -> Unit
) {
    CitySelectScreen(onCitySelected)
}

@Composable
fun CitySelectScreen(
    onCitySelected: (latitude: Double, longitude: Double) -> Unit,
    viewModel: CityViewModel = hiltViewModel()
) {

}