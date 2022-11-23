package com.example.kotlin_gb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_gb.app.App.Companion.getHistoryDao
import com.example.kotlin_gb.repository.LocalHistoryRepository
import com.example.kotlin_gb.repository.LocalHistoryRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalHistoryRepository = LocalHistoryRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.SuccessMultiWeather(historyRepository.getAllHistory())
    }
}