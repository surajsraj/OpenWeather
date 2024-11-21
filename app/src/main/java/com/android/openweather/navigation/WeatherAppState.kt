package com.android.openweather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberWeatherAppState(
    navController: NavHostController = rememberNavController()
): WeatherAppState {
    return remember(
        navController
    ) {
        WeatherAppState(navController)
    }
}

@Stable
class WeatherAppState(
    val navController: NavHostController
)