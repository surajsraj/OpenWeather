package com.android.openweather.ui.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.android.openweather.R
import com.android.openweather.model.util.getIconUrl
import com.android.openweather.model.weather.WeatherUiData
import com.android.openweather.navigation.CityDataCoordinates
import com.android.openweather.ui.util.UiState

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
    val uiState: UiState<WeatherUiData> by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler(true) {
        onBackClick.invoke()
    }
    LaunchedEffect(cityDataCoordinates) {
        cityDataCoordinates.apply {
            viewModel.getWeather(latitude, longitude)
        }
    }

    when (uiState) {
        is UiState.Loading -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                )
            }
        }

        is UiState.Success<WeatherUiData> -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text((uiState as UiState.Success<WeatherUiData>).data.name)
                Text((uiState as UiState.Success<WeatherUiData>).data.temp)
                AsyncImage(
                    modifier = Modifier.size(64.dp),
                    model = (uiState as UiState.Success<WeatherUiData>).data.getIconUrl(),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    error = painterResource(id = R.drawable.baseline_error_24),
                    placeholder = painterResource(id = R.drawable.baseline_cloud_download_24),
                )
            }
        }

        is UiState.Failure -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Something went wrong")
            }
        }
    }
}