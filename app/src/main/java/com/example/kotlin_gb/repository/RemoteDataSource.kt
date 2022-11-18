package com.example.kotlin_gb.repository

import com.example.kotlin_gb.BuildConfig
import com.example.kotlin_gb.utils.Const
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

// Это класс, где происходит запрос на сервер. Это наш источник данных. Здесь мы
// создаём инстанс OkHttp, формируем запрос и отправляем его. Результаты запроса станут
// обрабатываться во ViewModel — там будет находиться наш callback.

class RemoteDataSource {
    fun getWeatherDetails(requestLink: String, callback: Callback) {
        val builder = Request.Builder().apply {
            header(Const.X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
            url(requestLink)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}