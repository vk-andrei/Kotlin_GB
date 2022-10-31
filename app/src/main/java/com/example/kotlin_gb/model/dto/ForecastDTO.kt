package com.example.kotlin_gb.model.dto


import com.google.gson.annotations.SerializedName

data class ForecastDTO(
    @SerializedName("date")
    val date: String,
    @SerializedName("date_ts")
    val dateTs: Int,
    @SerializedName("moon_code")
    val moonCode: Int,
    @SerializedName("moon_text")
    val moonText: String,
    val parts: List<PartDTO>,
    val sunrise: String,
    val sunset: String,
    val week: Int
)