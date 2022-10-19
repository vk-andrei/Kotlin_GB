package com.example.kotlin_gb.viewmodel

import com.example.kotlin_gb.model.Weather

sealed class AppState {
    data class SuccessSingleWeather(val weatherData: Weather) :
        AppState() // приложение работает, данные отобража-ся

    data class SuccessMultiWeather(val weatherListData: List<Weather>) :
        AppState() // приложение работает, данные отобража-ся

    data class Error(val error: Throwable) : AppState() // в прилож-ии ошибка!

    object Loading : AppState()                         // приож-е загружает данные
}