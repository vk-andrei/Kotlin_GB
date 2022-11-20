package com.example.kotlin_gb.repository

import com.example.kotlin_gb.model.dto.WeatherDTO
import retrofit2.Callback

interface DetailsRepository {
    fun getWeatherDetailsFromServer(lat: Double, lon: Double, callback: Callback<WeatherDTO>)
}