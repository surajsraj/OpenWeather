package com.android.openweather.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.openweather.ui.search.CityDataRoute
import kotlinx.serialization.Serializable

@Serializable
data object CityDataRoute

fun NavGraphBuilder.cityDataScreen(
    onCitySelected: (latitude: Double, longitude: Double) -> Unit
) {
    composable<CityDataRoute> {
        CityDataRoute(onCitySelected)
    }
}