package com.example.kotlin_gb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_gb.databinding.ActivityMainBinding
import com.example.kotlin_gb.view.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
    }
}