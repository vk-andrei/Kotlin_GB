package com.example.kotlin_gb.repository

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import com.example.kotlin_gb.app.ContextProvider
import com.example.kotlin_gb.app.IContextProvider

// Нам нужен контекст, поэтому сделаем в APP контекстПровайдер и будем его брать оттуда

class ContactsRepositoryImpl(contextProvider: IContextProvider = ContextProvider) :
    ContactsRepository {

    // Чтобы воспользоваться Content Provider, надо получить инстанс класса ContentResolver и передать
    //в запрос строку URI для этого Provider. Android сам поймёт, какой Provider понадобится вызвать.
    //Чтобы получить список контактов на телефоне, надо передать URI
    //content://com.android.contacts/contacts или воспользоваться константой
    //ContactsContract.Contacts.CONTENT_URI.
    // Получаем ContentResolver у контекста
    private val contentResolver: ContentResolver = contextProvider.context.contentResolver

    @SuppressLint("Range")
    override fun getListOfContacts(): List<String> {

        //Content Provider работает через Cursor — механизм работы с базой данных,
        //при котором извлекается не весь запрос, а только текущая запись. Cursor, как правило, двигается по
        //записям.
        // Отправляем запрос на получение контактов и получаем ответ в виде Cursor
        val cursorWithContacts: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + "ASC"
        )

        // Сюда загрузим все контакты
        val answer = mutableListOf<String>()

        cursorWithContacts?.let { cursor ->
            cursor.moveToFirst()
            do {
                answer.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursorWithContacts?.close()

        return answer
    }
}