package com.example.kotlin_gb.model

import com.example.kotlin_gb.domain.Weather

class RepositoryRemoteImpl : Repository {
    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread {
            Thread.sleep(2000L)
        }.start()
        return Weather()
    }

    override fun getListWeather(): List<Weather> {
        Thread {
            Thread.sleep(5000L)
        }.start()
        return listOf(Weather())
    }
}