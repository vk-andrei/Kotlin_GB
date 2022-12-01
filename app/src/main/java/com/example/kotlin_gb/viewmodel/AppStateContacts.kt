package com.example.kotlin_gb.viewmodel

sealed class AppStateContacts {
    data class Success(val contacts: List<String>) : AppStateContacts()
    object Loading : AppStateContacts()
}