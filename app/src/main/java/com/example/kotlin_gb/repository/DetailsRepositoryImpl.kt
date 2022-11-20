package com.example.kotlin_gb.repository

import com.example.kotlin_gb.model.dto.WeatherDTO
import okhttp3.Callback

// имплементация репозитория, описанного выше в виде интерфейса
// в репозиторий мы передаём источник данных — так репозиторий получает данные извне.

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    ) {
        remoteDataSource.getWeatherAPIDetails(lat, lon, callback)
    }
}