package com.example.kotlin_gb.repository

import okhttp3.Callback

// имплементация репозитория, описанного выше в виде интерфейса
// в репозиторий мы передаём источник данных — так репозиторий получает данные извне.

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getWeatherDetailsFromServer(requestLink: String, callback: Callback) {
        remoteDataSource.getWeatherDetails(requestLink, callback)
    }
}