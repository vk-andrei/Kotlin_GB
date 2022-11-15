package com.example.kotlin_gb.model.dto


import com.google.gson.annotations.SerializedName

data class FactDTO(
    val temp: Int,
    @SerializedName("feels_like")
    val feelsLike: Int,
    val condition: String,
    val humidity: Int,
    val windSpeed: Double,
    @SerializedName("pressure_mm")
    val pressureMm: Int,
//    @SerializedName("pressure_pa")
//    val pressurePa: Int,
//    @SerializedName("wind_speed")
//    val daytime: String,
//    val icon: String,
//    @SerializedName("obs_time")
//    val obsTime: Int,
//    val polar: Boolean,
//    val season: String,
//    @SerializedName("temp_water")
//    val tempWater: Int,
//    @SerializedName("wind_dir")
//    val windDir: String,
//    @SerializedName("wind_gust")
//    val windGust: Double
)