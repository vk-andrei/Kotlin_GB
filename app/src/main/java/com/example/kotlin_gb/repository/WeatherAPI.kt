package com.example.kotlin_gb.repository

import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.utils.Const.Companion.X_YANDEX_API_KEY

import retrofit2.http.*
import retrofit2.*

//Этим интерфейсом мы описываем конкретный запрос на сервер — запрос на данные погоды с
//сервера Яндекса. Он формируется простым методом с аннотациями: указывается endpoint ссылки
//(v2/informers), заголовок (@Header), а два параметра (@Query) запроса передаются в метод как
//аргументы. Возвращает метод уже готовый класс с ответом от сервера (WeatherDTO). Синтаксис
//прост и понятен любому программисту.
//В отличие от OkHttp, в Retrofit многое делается через аннотации: формат запроса, отправка и
//получение данных, параметры, заголовки и многое другое. Ещё в Retrofit используются классы с
//такими же названиями, как и в OkHttp, но они не взаимозаменяемы.

interface WeatherAPI {

    @GET("v2/informers")
    fun getWeather(
        @Header(X_YANDEX_API_KEY) token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
}