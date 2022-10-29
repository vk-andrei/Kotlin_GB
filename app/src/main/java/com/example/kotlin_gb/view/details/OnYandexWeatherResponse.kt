package com.example.kotlin_gb.view.details

import com.example.kotlin_gb.model.dto.WeatherDTO

interface OnYandexWeatherResponse {
    fun onYandexWeatherResponse(weatherDTO: WeatherDTO)
}