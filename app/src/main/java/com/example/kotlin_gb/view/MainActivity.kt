package com.example.kotlin_gb.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_gb.R
import com.example.kotlin_gb.view.history.HistoryFragment
import com.example.kotlin_gb.view.weatherlist.WeatherListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemReselectedListener{
            when (it.itemId) {
                R.id.Home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, WeatherListFragment.newInstance()).commit()
                }
                R.id.History -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HistoryFragment.newInstance()).commit()
                }
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

    }
}