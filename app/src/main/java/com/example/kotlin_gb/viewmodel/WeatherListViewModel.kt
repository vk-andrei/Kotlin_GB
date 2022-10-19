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

    private lateinit var repositoryForSingleWeather: RepositoryForSingleWeather
    private lateinit var repositoryForMultiWeather: RepositoryForMultiWeather

    fun getLiveData(): MutableLiveData<AppState> {
        chooseRepository()
        return liveData
    }

    private fun chooseRepository() {
        repositoryForSingleWeather = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
        repositoryForMultiWeather = RepositoryLocalImpl()
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
    fun sendRequest(location: Location) {
        liveData.value = AppState.Loading
        if ((0..3).shuffled().first() == 1) {
            liveData.postValue(AppState.Error(error = Throwable(IllegalStateException("something WRONG"))))
        } else {
            liveData.postValue(
                AppState.SuccessMultiWeather(repositoryForMultiWeather.getListWeather(location))
            )
        }
    }

    override fun onCleared() {    // TODO HW
        super.onCleared()
    }
}
