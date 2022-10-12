package com.example.kotlin_gb.viewmodel

sealed class AppState {
    data class Success(val weatherData: Any) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}