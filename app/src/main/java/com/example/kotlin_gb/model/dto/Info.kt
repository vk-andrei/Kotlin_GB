package com.example.kotlin_gb.model.dto


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("lat")
    val lat: Int,
    @SerializedName("lon")
    val lon: Int,
    @SerializedName("url")
    val url: String
)