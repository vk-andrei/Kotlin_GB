package com.example.kotlin_gb.app

import android.app.Application
import androidx.room.Room
import com.example.kotlin_gb.room.HistoryDao
import com.example.kotlin_gb.room.HistoryDataBase
import java.lang.IllegalStateException

// Создадим базу через паттерн Singleton. Для этого определим статический метод, который будет
//возвращать DAO. Именно через DAO мы вносим данные в базу, удаляем или изменяем их.
//Что происходит в самом методе: если БД ещё не создана, то мы потокобезопасно формируем базу
//через метод Room.databaseBuilder, который принимает три аргумента — контекст, база и имя БД. В
//системе может храниться несколько баз с разными именами и структурами данных. Метод
//allowMainThreadQueries позволяет делать запросы из основного потока.
//Важно! К БД запрещено обращаться в основном потоке. Этот метод используется
//исключительно в качестве примера или тестирования. Вы уже знаете, как делать вызовы в
//отдельных потоках, и это будет вашим практическим заданием.
//Обязательно прописываем Application в манифесте

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                synchronized(HistoryDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext, HistoryDataBase::class.java, DB_NAME
                        )
                            .allowMainThreadQueries().build()
                    }
                }
            }
            return db!!.historyDao()
        }
    }
}