package com.android.openweather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost


@Composable
fun WeatherNavHost(
    appState: WeatherAppState
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = CityDataRoute
    ) {
        cityDataScreen(onCitySelected = navController::navigateToWeatherData)
        weatherDataScreen(onBackClick = navController::popBackStack)
    }
}