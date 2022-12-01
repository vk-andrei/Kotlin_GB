package com.example.kotlin_gb.repository

import com.example.kotlin_gb.model.Weather

// Два метода: получение истории запросов и сохранение очередного запроса в БД

interface LocalHistoryRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}