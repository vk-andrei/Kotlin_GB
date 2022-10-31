package com.example.kotlin_gb.view.details

import com.example.kotlin_gb.model.Weather

fun interface OnCityClickable {
    fun onCityClick(weather: Weather)
}