package com.example.kotlin_gb.repository

import com.example.kotlin_gb.room.model.Location
import com.example.kotlin_gb.room.model.Weather
import com.example.kotlin_gb.room.model.getRussianCities
import com.example.kotlin_gb.room.model.getWorldCities

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