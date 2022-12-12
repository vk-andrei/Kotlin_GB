package com.example.kotlin_gb.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.kotlin_gb.R
import com.example.kotlin_gb.utils.Const.Companion.CHANNEL_HIGH_PRIORITY_ID
import com.example.kotlin_gb.utils.Const.Companion.CHANNEL_LOW_PRIORITY_ID
import com.example.kotlin_gb.utils.Const.Companion.NOTIFICATION_ID
import com.example.kotlin_gb.view.contacts.ContactsFragment
import com.example.kotlin_gb.view.history.HistoryFragment
import com.example.kotlin_gb.view.maps.MapsFragment
import com.example.kotlin_gb.view.weatherlist.WeatherListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, WeatherListFragment.newInstance())
                        .addToBackStack("").commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.History -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HistoryFragment.newInstance())
                        .addToBackStack("").commitAllowingStateLoss()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.Contacts -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ContactsFragment.newInstance())
                        .addToBackStack("").commitAllowingStateLoss()
                }
                R.id.Maps -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MapsFragment())
                        .addToBackStack("").commitAllowingStateLoss()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

        // NOTIFICATION:
        pushNotification("Attention!", "We make new feature in our APP. Open and you will see!")
    }

    private fun pushNotification(title: String, body: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationHigh = NotificationCompat.Builder(this, CHANNEL_HIGH_PRIORITY_ID).apply {
            setContentTitle(title)
            setContentText(body)
            setSmallIcon(R.drawable.ic_marker_google_map)
            priority = NotificationCompat.PRIORITY_MAX
            //intent = PendingIntent(). TODO сделать чтобы открывалось это иди др приложение
        }.build()

        val notificationLow = NotificationCompat.Builder(this, CHANNEL_LOW_PRIORITY_ID).apply {
            setContentTitle(title)
            setContentText(body)
            setSmallIcon(R.drawable.ic_marker_google_map)
            priority = NotificationCompat.PRIORITY_MAX
        }.build()

        // После 26 версии доступно НЕСКОЛЬКО каналов для ПУШЕЙ (пользователь может отключать или все или некоторые)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelHigh = NotificationChannel(
                CHANNEL_HIGH_PRIORITY_ID, "CHANNEL HIGH", NotificationManager.IMPORTANCE_HIGH
            )
            channelHigh.description = "Channel for importance notifications"
            notificationManager.createNotificationChannel(channelHigh)
        }
        // до 26 версии ТОЛЬКО один канал для пушей: (Эта строчка нужна и для версии выше 26!)
        notificationManager.notify(NOTIFICATION_ID, notificationHigh)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelLow = NotificationChannel(
                CHANNEL_LOW_PRIORITY_ID, "CHANNEL LOW", NotificationManager.IMPORTANCE_LOW
            )
            channelLow.description = "Channel for NOT importance notifications"
            notificationManager.createNotificationChannel(channelLow)

        }
        // до 26 версии ТОЛЬКО один канал для пушей: (Эта строчка нужна и для версии выше 26!)
        notificationManager.notify(NOTIFICATION_ID, notificationLow)
    }
}