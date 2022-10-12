package com.example.kotlin_gb.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_gb.viewmodel.AppState
import java.lang.Thread.sleep

/* THE SAME:
class WeatherListViewModel : ViewModel() {
    val liveData = MutableLiveData<Any>()
}*/
class WeatherListViewModel(val liveData: MutableLiveData<AppState> = MutableLiveData()) :
    ViewModel() {

    fun sendRequest() {
        liveData.value = AppState.Loading
        Thread {
            sleep(200L)
            liveData.postValue(AppState.Success(Any()))
        }.start()
    }

}