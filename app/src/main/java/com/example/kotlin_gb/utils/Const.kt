package com.example.kotlin_gb.utils

import android.net.Uri
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class Const {
    companion object {
        const val REQUEST_METHOD = "GET"
        const val READ_TIMEOUT = 10000
        const val HTTPS_YANDEX_URL = "https://api.weather.yandex.ru/v2/informers"
        const val X_YANDEX_API_KEY = "X-Yandex-API-Key"

        // For DetailService
        const val DETAILS_INTENT_FILTER = "DETAILS_INTENT_FILTER"
        const val LATITUDE_EXTRA = "LATITUDE_EXTRA"
        const val LONGITUDE_EXTRA = "LONGITUDE_EXTRA"

                // Result of request:
        const val DETAILS_LOAD_RESULT_EXTRA = "DETAILS_LOAD_RESULT_EXTRA"
                // response:
        const val DETAILS_DATA_EMPTY_EXTRA = "DETAILS_DATA_EMPTY_EXTRA"
        const val DETAILS_INTENT_EMPTY_EXTRA = "DETAILS_INTENT_EMPTY_EXTRA"
        const val DETAILS_RESPONSE_EMPTY_EXTRA = "DETAILS_RESPONSE_EMPTY_EXTRA"
        const val DETAILS_RESPONSE_SUCCESS_EXTRA = "DETAILS_RESPONSE_SUCCESS_EXTRA"

        const val DETAILS_REQUEST_ERROR_EXTRA = "DETAILS_REQUEST_ERROR_EXTRA"
        const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "DETAILS_REQUEST_ERROR_MESSAGE_EXTRA"

        const val DETAILS_URL_MALFORMED_EXTRA = "DETAILS_URL_MALFORMED_EXTRA"

            // weather data
        const val DETAILS_TEMP_EXTRA = "DETAILS_TEMP_EXTRA"
        const val DETAILS_FEELS_LIKE_EXTRA = "DETAILS_FEELS_LIKE_EXTRA"
        const val DETAILS_CONDITION_EXTRA = "DETAILS_CONDITION_EXTRA"
        const val DETAILS_HUMIDITY_EXTRA = "DETAILS_HUMIDITY_EXTRA"
        const val DETAILS_PRESSURE_MM_EXTRA = "DETAILS_PRESSURE_MM_EXTRA"
        const val DETAILS_WIND_SPEED_EXTRA = "DETAILS_WIND_SPEED"
        const val DETAILS_NOW_DT_EXTRA = "DETAILS_NOW_DT_EXTRA"


        const val PROCESS_ERROR = "PROCESS_ERROR"
        const val TEMP_INVALID = -100
        const val FEELS_LIKE_INVALID = -100
        const val HUMIDITY_INVALID = -100
        const val PRESSURE_MM_INVALID = -100
        const val WIND_SPEED_INVALID = -100.0

        // sharedPref
        const val LIST_OF_CITIES_KEY = "LIST_OF_CITIES_KEY"
        const val RUSSIAN_KEY = "RUSSIAN_KEY"
        const val WORLD_KEY = "WORLD_KEY"

        const val YANDEX_API_SVG_ICON_URL = "https://yastatic.net/weather/i/icons/funky/dark/"
    }
}