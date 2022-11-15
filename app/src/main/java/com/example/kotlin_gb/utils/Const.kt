package com.example.kotlin_gb.utils

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



        const val DETAILS_TEMP_EXTRA = "DETAILS_TEMP_EXTRA"
        const val DETAILS_FEELS_LIKE_EXTRA = "DETAILS_FEELS_LIKE_EXTRA"
        const val DETAILS_CONDITION_EXTRA = "DETAILS_CONDITION_EXTRA"


    }
}