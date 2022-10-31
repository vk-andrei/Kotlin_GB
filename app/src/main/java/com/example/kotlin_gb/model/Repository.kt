package com.example.kotlin_gb.model

fun interface RepositorySingleWeatherGiver {
    fun getWeather(lat: Double, lon: Double): Weather
}

fun interface RepositoryMultiWeatherGiver {
    fun getListWeather(location: Location): List<Weather>
}