package com.example.kotlin_gb.repository

import com.example.kotlin_gb.room.model.Location
import com.example.kotlin_gb.room.model.Weather

fun interface RepositorySingleWeatherGiver {
    fun getWeather(lat: Double, lon: Double): Weather
}

fun interface RepositoryMultiWeatherGiver {
    fun getListWeather(location: Location): List<Weather>
}