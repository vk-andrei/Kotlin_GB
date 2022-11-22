package com.example.kotlin_gb.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.kotlin_gb.R
import com.example.kotlin_gb.model.City
import com.example.kotlin_gb.model.Weather
import com.example.kotlin_gb.room.HistoryEntity
import com.google.android.material.snackbar.Snackbar

object Utils {

    // Функции-расширения
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

    fun View.showSnackErrorWithAction(
        errorTitle: String = R.string.snack_bar_error_title.toString(),
        snackDuration: Int,
        setActionName: String,
        block: (v: View) -> Unit
    ) {
        Snackbar.make(this, errorTitle, snackDuration).setAction(setActionName, block).show()
    }

    fun View.showSnackError(
        errorTitle: String = R.string.snack_bar_error_title.toString(),
        snackDuration: Int
    ) {
        Snackbar.make(this, errorTitle, snackDuration).show()
    }

    // Так как DAO работает с Entity, надо написать два дополнительных метода для конвертации данных
    fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
        return entityList.map {
            Weather(City(it.city, 0.0, 0.0), it.temperature, 0, it.condition)
        }
    }

    fun convertWeatherToEntity(weather: Weather): HistoryEntity {
        return HistoryEntity(0, weather.city.toString(), weather.temperature, weather.condition)
    }

}