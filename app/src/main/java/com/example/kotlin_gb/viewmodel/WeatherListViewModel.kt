package com.example.kotlin_gb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_gb.model.*

/* THE SAME:
class WeatherListViewModel : ViewModel() {
    val liveData = MutableLiveData<Any>()
}*/
class WeatherListViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
) :
    ViewModel() {

    lateinit var repositorySingleWeatherGiver: RepositorySingleWeatherGiver
    lateinit var repositoryMultiWeatherGiver: RepositoryMultiWeatherGiver

    fun getLiveData(): MutableLiveData<AppState> {
        chooseRepository()
        return liveData
    }

    private fun chooseRepository() {
        repositorySingleWeatherGiver = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
        repositoryMultiWeatherGiver = RepositoryLocalImpl()
    }

    private fun isConnection(): Boolean {
        return false
    }

    fun getRussianList() {
        sendRequest(Location.Russian)
    }

    fun getWorldList() {
        sendRequest(Location.World)
    }

    // setValue (value) - обновление данных из оснного потока
    // postValue - обновление данных из рабочего потока
    private fun sendRequest(location: Location) {
        liveData.value = AppState.Loading
        if ((0..2).shuffled().first() == 1) {
            Thread {
                Thread.sleep(2000L)
                liveData.postValue(
                    AppState.Error(error = Throwable(IllegalStateException("livedata.error")))
                )
            }.start()
        } else {
            Thread {
                Thread.sleep(2000L)
                liveData.postValue(
                    AppState.SuccessMultiWeather(repositoryMultiWeatherGiver.getListWeather(location))
                )
            }.start()
        }
    }

    override fun onCleared() {    // TODO HW
        super.onCleared()
    }
}


/*liveData.value = AppState.Loading
if ((0..3).shuffled().first() == 1) {
    liveData.postValue(AppState.Error(error = Throwable(IllegalStateException("something WRONG"))))
} else {
    liveData.postValue(
        AppState.SuccessMultiWeather(repositoryForMultiWeather.getListWeather(location))
    )
}*/
