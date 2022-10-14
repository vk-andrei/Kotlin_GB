package com.example.kotlin_gb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_gb.model.Repository
import com.example.kotlin_gb.model.RepositoryLocalImpl
import com.example.kotlin_gb.model.RepositoryRemoteImpl
import java.lang.IllegalStateException

/* THE SAME:
class WeatherListViewModel : ViewModel() {
    val liveData = MutableLiveData<Any>()
}*/
class WeatherListViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
) :
    ViewModel() {

    lateinit var repository: Repository

    fun getLiveData(): MutableLiveData<AppState> {
        chooseRepository()
        return liveData
    }

    private fun chooseRepository() {
        repository = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
    }

    private fun isConnection(): Boolean {
        return false
    }

    fun sendRequest() {
        liveData.value = AppState.Loading
        if ((0..3).shuffled().first() == 1) {
            liveData.postValue(AppState.Error(error = Throwable(IllegalStateException("something WRONG"))))
        } else {
            liveData.postValue(
                AppState.Success(repository.getWeather(55.755826, 37.617299900000035))
            )
        }
    }
}