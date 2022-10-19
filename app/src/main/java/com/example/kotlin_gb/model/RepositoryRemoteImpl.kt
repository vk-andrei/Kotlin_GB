package com.example.kotlin_gb.model

class RepositoryRemoteImpl : RepositoryForSingleWeather {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}