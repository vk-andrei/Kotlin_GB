package com.example.kotlin_gb.view.details

import com.example.kotlin_gb.room.model.Weather

fun interface OnCityClickable {
    fun onCityClick(weather: Weather)
}