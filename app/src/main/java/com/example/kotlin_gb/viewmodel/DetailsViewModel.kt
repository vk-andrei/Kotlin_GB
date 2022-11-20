package com.example.kotlin_gb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_gb.model.convertWeatherDTOtoWeather
import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.repository.DetailsRepositoryImpl
import com.example.kotlin_gb.repository.RemoteDataSource
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

private const val SERVER_ERROR = "SERVER_ERROR"
private const val REQUEST_ERROR = "REQUEST_ERROR"
private const val CORRUPTED_DATA = "CORRUPTED_DATA"

// Создаём LiveData для передачи данных, репозиторий для получения данных и два
// метода. Первый метод возвращает LiveData, чтобы на неё подписаться. Второй метод осуществляет
// запрос на сервер через репозиторий

class DetailsViewModel(
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRemoteImpl: DetailsRepositoryImpl = DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getLiveData() = detailsLiveData

    fun getWeatherDetailsFromServer(requestLink: String) {
        detailsRemoteImpl.getWeatherDetailsFromServer(requestLink, callback)
    }

    private val callback = object : Callback {
        override fun onResponse(call: Call, response: Response) {
            val serverResponse = response.body()?.string()
            detailsLiveData.postValue(
                if (serverResponse != null && response.isSuccessful) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call, e: IOException) {
            detailsLiveData.postValue(AppState.Error(Throwable(e.message ?: REQUEST_ERROR)))
        }

    }

    private fun checkResponse(serverResponse: String): AppState {
        val weatherDTO: WeatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)

        // TODO почему он пишет, что они не могут быть НУЛ??? (потому что выше проверяли ---->  if (serverResponse != null && response.isSuccessful) ? )
        return if (weatherDTO.fact.temp == null || weatherDTO.fact.feelsLike == null
            || weatherDTO.fact.humidity == null || weatherDTO.fact.pressureMm == null
            || weatherDTO.fact.windSpeed == null || weatherDTO.nowDt == null
        ) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.SuccessSingleWeather(convertWeatherDTOtoWeather(weatherDTO))
        }

    }


}