package com.example.kotlin_gb.view.weatherlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}