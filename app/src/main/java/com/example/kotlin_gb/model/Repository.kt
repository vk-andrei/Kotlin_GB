package com.example.kotlin_gb.model

interface Repository {

    fun getWeather(lat: Double, lon: Double): Weather
    fun getListWeather(): List<Weather>

}