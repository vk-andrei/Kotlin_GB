package com.example.kotlin_gb.view.weatherlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_gb.databinding.FragmentWeatherListRecyclerItemBinding
import com.example.kotlin_gb.model.Weather
import com.example.kotlin_gb.view.details.OnCityClickable

class WeatherListAdapter(
    private val dataList: List<Weather>,
    private val callback: OnCityClickable
) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

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

    inner class WeatherViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {  // inner --> to see "callback"
        fun bind(weather: Weather) {
            val binding = FragmentWeatherListRecyclerItemBinding.bind(itemView)
            binding.tvCityName.text = weather.city.name
            binding.root.setOnClickListener {
                callback.onCityClick(weather)
            }
        }
    }
}
