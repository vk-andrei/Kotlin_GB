package com.example.kotlin_gb.repository

import com.example.kotlin_gb.room.model.Weather

class RepositoryRemoteImpl : RepositorySingleWeatherGiver {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}