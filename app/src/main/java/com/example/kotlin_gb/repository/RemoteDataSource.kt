package com.example.kotlin_gb.repository

import com.example.kotlin_gb.BuildConfig
import com.example.kotlin_gb.room.model.dto.WeatherDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Здесь всего один метод, который принимает широту и долготу. Так как запрос формируется в
//интерфейсе WeatherAPI, создавать заранее ссылку не требуется, достаточно передать долготу и
//широту. Третьим аргументом выступает callback, но уже из библиотеки Retrofit, где в качестве
//дженерика указывается тип возвращаемых данных.
//Запрос создаётся сразу и присваивается переменной weatherApi. Он формируется достаточно
//просто: через статический builder указываем базовую ссылку, добавляем конвертер — знакомый нам
//Gson, но работающий теперь «под капотом» Retrofit.

class RemoteDataSource {

    private val weatherAPI = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .build().create(WeatherAPI::class.java)

    fun getWeatherAPIDetails(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {
        weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon).enqueue(callback)
    }
}