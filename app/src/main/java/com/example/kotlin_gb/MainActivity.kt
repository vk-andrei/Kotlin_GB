package com.example.kotlin_gb

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_gb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        setPersons()
    }

    private fun setListeners() {
        binding.btnOk.setOnClickListener {
            Log.d("TAG", "The btnOk is pushed")
        }
    }

    private fun setPersons() {
        val personList = mutableListOf<Person>()
        personList.add(Person("Ivan", 21, 3))
        personList.add(Person("Niko", 20, 2))
        personList.add(Person("Mark", 39, 1))
    }


}