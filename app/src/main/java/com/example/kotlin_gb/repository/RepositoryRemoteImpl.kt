package com.example.kotlin_gb.repository

import com.example.kotlin_gb.model.Weather

class RepositoryRemoteImpl : RepositorySingleWeatherGiver {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}