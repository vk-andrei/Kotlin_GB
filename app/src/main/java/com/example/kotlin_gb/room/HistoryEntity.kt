package com.example.kotlin_gb.room

import androidx.room.Entity
import androidx.room.PrimaryKey

//Чтобы описать сущность, надо указать в классе соответствующую аннотацию @Entity. А также
//аннотацией @PrimaryKey мы указали, что в таблице — ключ id. Он будет генерироваться
//автоматически (autoGenerate = true), и нам не надо заботиться о содержании этого ключа.

@Entity
class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long, // Auto generating
    val city: String,
    val country: String,
    val temperature: Int,
    val condition: String,
    val icon: String
)