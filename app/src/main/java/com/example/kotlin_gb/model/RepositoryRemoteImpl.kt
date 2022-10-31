package com.example.kotlin_gb.model

class RepositoryRemoteImpl : RepositorySingleWeatherGiver {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}