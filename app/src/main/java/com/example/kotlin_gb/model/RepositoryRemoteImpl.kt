package com.example.kotlin_gb.model

import com.example.kotlin_gb.domain.Weather

class RepositoryRemoteImpl : Repository {
    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread {
            Thread.sleep(200L)
        }.start()
        return Weather()
    }

    override fun getListWeather(): List<Weather> {
        Thread {
            Thread.sleep(500L)
        }.start()
        return listOf(Weather())
    }
}