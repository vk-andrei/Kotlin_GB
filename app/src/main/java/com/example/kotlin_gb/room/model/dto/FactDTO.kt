package com.example.kotlin_gb.room.model.dto


import com.google.gson.annotations.SerializedName

data class FactDTO(
    val temp: Int,
    @SerializedName("feels_like")
    val feelsLike: Int,
    val condition: String,
    val humidity: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("pressure_mm")
    val pressureMm: Int,
    val icon: String
)