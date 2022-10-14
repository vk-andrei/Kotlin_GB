package com.example.kotlin_gb.viewmodel

import com.example.kotlin_gb.domain.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}