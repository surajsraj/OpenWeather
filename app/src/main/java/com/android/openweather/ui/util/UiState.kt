package com.android.openweather.ui.util

sealed class UiState<out T : Any> {
    data object Loading : UiState<Nothing>()
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data object Failure : UiState<Nothing>()
}