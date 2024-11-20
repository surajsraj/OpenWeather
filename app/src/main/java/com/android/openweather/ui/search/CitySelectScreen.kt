package com.android.openweather.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.openweather.R
import com.android.openweather.model.geolocation.CityUiData
import com.android.openweather.ui.util.UiState

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
    val uiState: UiState<List<CityUiData>> by viewModel.uiStateOfCityList.collectAsStateWithLifecycle()
    val searchTextState by viewModel.searchTextState
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = {
            /*
            * Use runtime location permission on click of button to ask for location,
            * if permission was provided, check if location is turned on,
            * if turned on then use LocationManager and get latitude and longitude.
            * There are multiple negative scenarios to handle here,
            * if permission is not provided once, twice, etc. It will go as a separate UseCase class.
            * If location is not turned on, use play services to show dialog to turn it on etc.
            * Once latitude and longitude is obtained proceed to WeatherDataScreen.
            * */
            onCitySelected.invoke(51.5072, 0.1276)
        }, modifier = Modifier.padding(top = 4.dp)) {
            Text(stringResource(R.string.get_location))
        }
        Spacer(Modifier.height(16.dp))
        Text(stringResource(R.string.location_option))
        Spacer(Modifier.height(16.dp))
        SearchBar(
            text = searchTextState,
            onTextChange = { viewModel.updateSearchTextState(it) },
            onCloseClicked = { viewModel.updateSearchTextState("") },
            onSearchClicked = { viewModel.getCityData(it) },
        )
        Spacer(Modifier.height(16.dp))
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

            is UiState.Success<List<CityUiData>> -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LazyColumn {
                        if (uiState is UiState.Success<List<CityUiData>>) {
                            (uiState as UiState.Success<List<CityUiData>>).data.let {
                                items(it) { cityData ->
                                    Button(onClick = {
                                        onCitySelected.invoke(cityData.latitude, cityData.longitude)
                                    }, modifier = Modifier.padding(top = 4.dp)) {
                                        Text(cityData.name)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is UiState.Failure -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(R.string.ui_error))
                }
            }

            is UiState.Initial -> {}
        }
    }
}