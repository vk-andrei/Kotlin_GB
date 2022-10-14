package com.example.kotlin_gb.model

import com.example.kotlin_gb.domain.Weather

class RepositoryLocalImpl: Repository {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }

    override fun getListWeather(): List<Weather> {
        return listOf(Weather())
    }
}