package com.example.kotlin_gb.room.model

sealed class Location {
    object Russian : Location()
    object World : Location()
}