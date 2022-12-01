package com.example.kotlin_gb.viewmodel

import com.example.kotlin_gb.room.model.Weather

sealed class AppState {
    // приложение работает, данные отобража-ся
    data class SuccessSingleWeather(val weather: Weather) : AppState()

    // приложение работает, данные отобража-ся
    data class SuccessMultiWeather(val weatherListData: List<Weather>) : AppState()

    // в прилож-ии ошибка!
    data class Error(val error: Throwable) : AppState()

    // приож-е загружает данные
    object Loading : AppState()
}