package com.example.kotlin_gb.model.dto


import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("fact")
    val fact: FactDTO,
    @SerializedName("now_dt")
    val nowDt: String
//    @SerializedName("forecast")
//    val forecast: ForecastDTO,
//    @SerializedName("info")
//    val info: InfoDTO,
//    @SerializedName("now")
//    val now: Int,

)