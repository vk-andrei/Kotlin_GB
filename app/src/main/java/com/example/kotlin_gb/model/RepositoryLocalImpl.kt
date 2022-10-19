package com.example.kotlin_gb.model

class RepositoryLocalImpl : Repository {
    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread.sleep(3_000)
        return Weather()
    }

    override fun getListWeather(): List<Weather> {
        Thread.sleep(3_000)
        return listOf(Weather())
    }
}