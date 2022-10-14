package com.example.kotlin_gb.model

import com.example.kotlin_gb.domain.Weather

interface Repository {

    fun getWeather(lat: Double, lon: Double): Weather
    fun getListWeather(): List<Weather>

}