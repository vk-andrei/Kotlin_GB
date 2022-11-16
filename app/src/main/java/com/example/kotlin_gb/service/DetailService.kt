package com.example.kotlin_gb.service

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlin_gb.BuildConfig
import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.utils.Const
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_CONDITION_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_DATA_EMPTY_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_FEELS_LIKE_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_HUMIDITY_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_INTENT_EMPTY_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_INTENT_FILTER
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_LOAD_RESULT_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_NOW_DT_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_PRESSURE_MM_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_REQUEST_ERROR_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_REQUEST_ERROR_MESSAGE_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_RESPONSE_EMPTY_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_RESPONSE_SUCCESS_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_TEMP_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_URL_MALFORMED_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_WIND_SPEED_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.LATITUDE_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.LONGITUDE_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.READ_TIMEOUT
import com.example.kotlin_gb.utils.Const.Companion.REQUEST_METHOD
import com.example.kotlin_gb.utils.Const.Companion.X_YANDEX_API_KEY
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

// Мы организовали класс таким образом, чтобы в интенте бродкаста всегда отправлялся флаг,
// указывающий на результат работы сервиса: во фрагменте через when мы сможем прочитать и
// обработать результат.

class DetailService(name: String = "DetailService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val lat = intent.getDoubleExtra(LATITUDE_EXTRA, 0.0)
            val lon = intent.getDoubleExtra(LONGITUDE_EXTRA, 0.0)
            if (lat == 0.0 && lon == 0.0) {
                onEmptyData()
            } else {
                loadWeather(lat, lon)
            }
        }
    }

    private fun onEmptyIntent() {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun loadWeather(lat: Double, lon: Double) {
        try {
            val uri = URL("${Const.HTTPS_YANDEX_URL}?lat=${lat}&lon=${lon}")
            lateinit var myConnection: HttpsURLConnection
            try {
                myConnection = uri.openConnection() as HttpsURLConnection
                myConnection.apply {
                    requestMethod = REQUEST_METHOD
                    readTimeout = READ_TIMEOUT
                    addRequestProperty(X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                }
                val inputSystem = myConnection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val weatherDTO: WeatherDTO =
                    Gson().fromJson(inputStreamReader, WeatherDTO::class.java)
                onDetailServiceYandexResponse(weatherDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                myConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    private fun onDetailServiceYandexResponse(weatherDTO: WeatherDTO) {
        val fact = weatherDTO.fact
        if (fact == null) {   // TODO Пишет, что НУЛ тут невозможен. Почему так?
            onEmptyResponse()
        } else {
            onSuccessResponse(
                fact.temp,
                fact.feelsLike,
                fact.condition,
                fact.humidity,
                fact.pressureMm,
                fact.windSpeed,
                weatherDTO.nowDt
            )
        }
    }

    private fun onErrorRequest(error: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onSuccessResponse(
        temp: Int,
        feelsLike: Int,
        condition: String,
        humidity: Int,
        pressureMm: Int,
        windSpeed: Double,
        nowDt: String
    ) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_TEMP_EXTRA, temp)
        broadcastIntent.putExtra(DETAILS_FEELS_LIKE_EXTRA, feelsLike)
        broadcastIntent.putExtra(DETAILS_CONDITION_EXTRA, condition)
        broadcastIntent.putExtra(DETAILS_HUMIDITY_EXTRA, humidity)
        broadcastIntent.putExtra(DETAILS_PRESSURE_MM_EXTRA, pressureMm)
        broadcastIntent.putExtra(DETAILS_WIND_SPEED_EXTRA, windSpeed)
        broadcastIntent.putExtra(DETAILS_NOW_DT_EXTRA, nowDt)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

}
