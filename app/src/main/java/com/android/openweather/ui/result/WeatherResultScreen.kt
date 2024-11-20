package com.android.openweather.ui.result

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.openweather.navigation.CityDataCoordinates

@Composable
fun WeatherRoute(
    onBackClick: () -> Unit,
    cityDataCoordinates: CityDataCoordinates
) {
    WeatherResultScreen(onBackClick, cityDataCoordinates)
}

@Composable
fun WeatherResultScreen(
    onBackClick: () -> Unit,
    cityDataCoordinates: CityDataCoordinates,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    BackHandler(true) {
        onBackClick.invoke()
    }
    LaunchedEffect(cityDataCoordinates) {
        cityDataCoordinates.apply {
            viewModel.getWeather(latitude, longitude)
        }
    }
}