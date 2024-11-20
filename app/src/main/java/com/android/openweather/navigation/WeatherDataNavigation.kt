package com.android.openweather.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.android.openweather.ui.result.WeatherRoute
import kotlinx.serialization.Serializable

@Serializable
data class CityDataCoordinates(val latitude: Double, val longitude: Double)

fun NavController.navigateToWeatherData(latitude: Double, longitude: Double) =
    navigate(route = CityDataCoordinates(latitude, longitude))

fun NavGraphBuilder.weatherDataScreen(
    onBackClick: () -> Unit
) {
    composable<CityDataCoordinates> { backStackEntry ->
        val cityDataCoordinates = backStackEntry.toRoute<CityDataCoordinates>()
        WeatherRoute(onBackClick, cityDataCoordinates)
    }
}