package com.android.openweather.network.util

sealed class NetworkResult<out T : Any> {
    data object Fetching : NetworkResult<Nothing>()
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
}