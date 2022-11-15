package com.example.kotlin_gb.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_gb.R
import com.example.kotlin_gb.view.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

    }
}