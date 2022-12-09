package com.example.kotlin_gb.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_gb.R
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
    }
}