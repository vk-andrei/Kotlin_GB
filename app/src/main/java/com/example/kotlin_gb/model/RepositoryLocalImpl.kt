package com.example.kotlin_gb.model

class RepositoryLocalImpl : RepositoryForSingleWeather, RepositoryForMultiWeather {
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