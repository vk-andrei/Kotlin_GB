package com.example.kotlin_gb.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/* THE SAME:
class WeatherListViewModel : ViewModel() {
    val liveData = MutableLiveData<Any>()
}*/
class WeatherListViewModel(val liveData: MutableLiveData<Any> = MutableLiveData()) : ViewModel() {


}