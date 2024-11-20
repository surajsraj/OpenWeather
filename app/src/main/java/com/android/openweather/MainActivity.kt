package com.android.openweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.android.openweather.navigation.WeatherAppState
import com.android.openweather.navigation.WeatherNavHost
import com.android.openweather.navigation.rememberWeatherAppState
import com.android.openweather.ui.theme.OpenWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState: WeatherAppState = rememberWeatherAppState()
            OpenWeatherTheme {
                WeatherNavHost(appState)
            }
        }
    }
}