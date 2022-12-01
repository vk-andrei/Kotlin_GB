package com.example.kotlin_gb.repository

import com.example.kotlin_gb.room.HistoryDao
import com.example.kotlin_gb.room.model.Weather
import com.example.kotlin_gb.utils.Utils.convertHistoryEntityToWeather
import com.example.kotlin_gb.utils.Utils.convertWeatherToEntity

// В качестве источника данных воспользуемся DAO. Так как DAO работает с Entity, надо написать два
// дополнительных метода для конвертации данных в DataUtils

class LocalHistoryRepositoryImpl(private val localDataSource: HistoryDao) : LocalHistoryRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }
}