package com.android.openweather.ui.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.openweather.R
import com.android.openweather.ui.model.CityUiData
import com.android.openweather.ui.util.PermissionRequest
import com.android.openweather.ui.util.UiState
import com.android.openweather.ui.util.getUserLocation

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
    val context = LocalContext.current
    val uiState: UiState<List<CityUiData>> by viewModel.uiStateOfCityList.collectAsStateWithLifecycle()
    var shouldGetLocation by remember { mutableStateOf(false) }
    var shouldShowLoading by remember { mutableStateOf(false) }
    val searchTextState by viewModel.searchTextState

    /*
     * There are multiple negative scenarios to handle here.
     * If permission is not provided once, twice, showing rationale, etc. or if location is not turned on.
     * All these should go as a separate LocationDetails UseCase.
     */
    if (shouldGetLocation) {
        PermissionRequest {
            getUserLocation(context, onLocationFetching = {
                shouldShowLoading = true
            }) { location ->
                shouldGetLocation = false
                shouldShowLoading = false
                location?.let {
                    onCitySelected.invoke(location.latitude, location.longitude)
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (shouldShowLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Button(onClick = {
                shouldGetLocation = true
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
            Spacer(Modifier.height(50.dp))
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyColumn {
                            if (uiState is UiState.Success<List<CityUiData>>) {
                                (uiState as UiState.Success<List<CityUiData>>).data.let {
                                    items(it) { cityData ->
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .border(2.dp, Color.Black)
                                                .clickable {
                                                    onCitySelected.invoke(
                                                        cityData.latitude,
                                                        cityData.longitude
                                                    )
                                                }, horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(cityData.name, modifier = Modifier.padding(10.dp))
                                            Icon(
                                                modifier = Modifier.padding(10.dp),
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = null
                                            )
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.ui_error))
                    }
                }

                is UiState.Initial -> {}
            }
        }
    }
}