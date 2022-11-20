package com.example.kotlin_gb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_gb.model.convertWeatherDTOtoWeather
import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.repository.DetailsRepositoryImpl
import com.example.kotlin_gb.repository.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "SERVER_ERROR"
private const val REQUEST_ERROR = "REQUEST_ERROR"
private const val CORRUPTED_DATA = "CORRUPTED_DATA"

// Создаём LiveData для передачи данных, репозиторий для получения данных и два
// метода. Первый метод возвращает LiveData, чтобы на неё подписаться. Второй метод осуществляет
// запрос на сервер через репозиторий

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRemoteImpl: DetailsRepositoryImpl = DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    //fun getLiveData() = detailsLiveData

    fun getWeatherFromRemoteSource(lat:Double, lon:Double) {
        detailsLiveData.value = AppState.Loading
        detailsRemoteImpl.getWeatherDetailsFromServer(lat, lon, callback)
    }

    private val callback = object : Callback<WeatherDTO> {
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse: WeatherDTO? = response.body()
            detailsLiveData.postValue(
                if (serverResponse != null && response.isSuccessful) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    private fun checkResponse(serverResponse: WeatherDTO): AppState {

        // TODO почему он пишет, что они не могут быть НУЛ??? (потому что выше проверяли ---->  if (serverResponse != null && response.isSuccessful) ? )
        return if (serverResponse.fact.temp == null || serverResponse.fact.feelsLike == null
            || serverResponse.fact.humidity == null || serverResponse.fact.pressureMm == null
            || serverResponse.fact.windSpeed == null || serverResponse.nowDt == null
        ) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.SuccessSingleWeather(convertWeatherDTOtoWeather(serverResponse))
        }
    }
}