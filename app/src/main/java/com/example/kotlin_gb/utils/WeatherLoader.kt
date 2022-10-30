package com.example.kotlin_gb.utils

import android.util.Log
import android.widget.Toast
import com.example.kotlin_gb.BuildConfig
import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.view.details.OnYandexWeatherResponse
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    fun requestWeatherFromYandex(lat: Double, lon: Double, onResponse: OnYandexWeatherResponse) {

        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        var myConnection: HttpsURLConnection? = null
        myConnection = uri.openConnection() as HttpsURLConnection
        myConnection.requestMethod = "GET"  // надо или нет???
        myConnection.readTimeout = 5000     // надо или нет???
        myConnection.addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)

        Thread {
            if (myConnection.responseCode == 200) {
                val inputSystem = myConnection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val weatherDTO = Gson().fromJson(inputStreamReader, WeatherDTO::class.java)

                onResponse.onYandexWeatherResponse(weatherDTO)

                inputStreamReader.close()
                inputSystem.close()

            } else {
                Log.d("TAG", "WEATHER_LOADER, responseCode != 200")
                onResponse.onFailedResponse()
            }

        }.start()
    }
}