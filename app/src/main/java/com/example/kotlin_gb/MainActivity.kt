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

        // создаем сущность сразу без  объявления класса
        val andrei = object {
            val name = "andrei"
            var age = 39
        }
        andrei.age = 40

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

        val personNew = personList[2].copy(name = "Vladimir")
        personList.add(personNew)

        for (person in personList) {
            Log.d("TAG", "$person")
        }
    }
}