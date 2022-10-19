package com.example.kotlin_gb.model

fun interface RepositoryForSingleWeather {
    fun getWeather(lat: Double, lon: Double): Weather
}

fun interface RepositoryForMultiWeather {
    fun getListWeather(location: Location): List<Weather>
}