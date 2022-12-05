package com.example.kotlin_gb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_gb.repository.ContactsRepository
import com.example.kotlin_gb.repository.ContactsRepositoryImpl

class ContactsViewModel(private val repositoryContacts: ContactsRepository = ContactsRepositoryImpl()) :
    ViewModel() {

    val contacts: MutableLiveData<AppStateContacts> = MutableLiveData()

    fun getContacts() {
        contacts.value = AppStateContacts.Loading
        val answer = repositoryContacts.getListOfContacts()
        contacts.value = AppStateContacts.Success(answer)
    }


}