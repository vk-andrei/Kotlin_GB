package com.example.kotlin_gb.utils

import android.util.Log
import com.example.kotlin_gb.BuildConfig
import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.view.details.OnYandexWeatherResponse
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    fun requestWeatherFromYandex(lat: Double, lon: Double, onResponse: OnYandexWeatherResponse) {

        val uri = URL("${Const.HTTPS_YANDEX_URL}?lat=${lat}&lon=${lon}")
        var myConnection: HttpsURLConnection? = null
        myConnection = uri.openConnection() as HttpsURLConnection
        myConnection.requestMethod = Const.REQUEST_METHOD
        myConnection.readTimeout = Const.READ_TIMEOUT
        myConnection.addRequestProperty(Const.X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)

        Thread {
            if (myConnection.responseCode == 200) {
                Log.d("TAG", "WEATHER_LOADER, responseCode = 200")
                val inputSystem = myConnection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val weatherDTO = Gson().fromJson(inputStreamReader, WeatherDTO::class.java)

                onResponse.onYandexWeatherResponse(weatherDTO)

                inputStreamReader.close()
                inputSystem.close()

            } else {
                // TODO Toast -> responseCode != 200
                Log.d("TAG", "WEATHER_LOADER, responseCode != 200")
                onResponse.onFailedResponse()
            }
        }.start()
    }
}