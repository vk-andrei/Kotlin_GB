package com.example.kotlin_gb.room.model.dto

import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("fact")
    val fact: FactDTO,
    @SerializedName("now_dt")
    val nowDt: String
)