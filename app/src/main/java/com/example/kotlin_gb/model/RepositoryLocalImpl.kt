package com.example.kotlin_gb.model

import com.example.kotlin_gb.domain.Weather

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