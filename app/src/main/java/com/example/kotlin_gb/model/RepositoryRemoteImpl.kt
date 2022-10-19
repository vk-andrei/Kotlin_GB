package com.example.kotlin_gb.model

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