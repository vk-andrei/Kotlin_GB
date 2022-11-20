package com.example.kotlin_gb.repository

import com.example.kotlin_gb.model.Location
import com.example.kotlin_gb.model.Weather
import com.example.kotlin_gb.model.getRussianCities
import com.example.kotlin_gb.model.getWorldCities

class RepositoryLocalImpl : RepositorySingleWeatherGiver, RepositoryMultiWeatherGiver {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }

    override fun getListWeather(location: Location): List<Weather> {
        return when (location) {
            Location.Russian -> {
                getRussianCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
    }
}