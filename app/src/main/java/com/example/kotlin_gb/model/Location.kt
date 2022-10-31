package com.example.kotlin_gb.model

sealed class Location {
    object Russian : Location()
    object World : Location()
}