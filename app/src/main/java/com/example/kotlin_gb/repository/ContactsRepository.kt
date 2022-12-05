package com.example.kotlin_gb.repository

interface ContactsRepository {
    fun getListOfContacts(): List<String>
}