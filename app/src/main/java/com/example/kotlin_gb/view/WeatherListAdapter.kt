package com.example.kotlin_gb.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentWeatherListRecyclerItemBinding
import com.example.kotlin_gb.model.Weather

class WeatherListAdapter(private val dataList: List<Weather>) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {


    interface CityClickable {
        fun onCityClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding =
            FragmentWeatherListRecyclerItemBinding.inflate(LayoutInflater.from(parent.context))
        return WeatherViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            val binding = FragmentWeatherListRecyclerItemBinding.bind(itemView)
            binding.tvCityName.text = weather.city.name
        }
    }
}
