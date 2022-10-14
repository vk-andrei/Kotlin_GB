package com.example.kotlin_gb.domain

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 10,
    val feelsLike: Int = 11
)

data class City(
    val name: String,
    val lat: Double,
    val lon: Double
)

fun getDefaultCity(): City {
    return City("Moscow", 55.755826, 37.617299900000035)
}