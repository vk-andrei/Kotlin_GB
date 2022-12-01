package com.example.kotlin_gb.view.history

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentHistoryRecyclerItemBinding
import com.example.kotlin_gb.room.model.Weather
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

// Адаптер для RecyclerView абсолютно стандартный. В методе bind отображаем элемент списка
// через объединение данных и вешаем listener для наглядности. Метод setData добавляет данные для
// отображения. Этот метод создан неслучайно: так как данные грузятся из БД асинхронно, то
// RecyclerView и адаптер подготовятся гораздо раньше, чем появятся данные. Поэтому мы не можем
// передавать данные, например, в конструктор адаптера.

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecycleItemViewHolder>() {

    private var dataWeatherHistoryArray: List<Weather> = arrayListOf()

    fun setData(data: List<Weather>) {
        this.dataWeatherHistoryArray = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleItemViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_history_recycler_item, parent, false)
        return RecycleItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecycleItemViewHolder, position: Int) {
        holder.bind(dataWeatherHistoryArray[position])
    }

    override fun getItemCount(): Int {
        return dataWeatherHistoryArray.size
    }

    inner class RecycleItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FragmentHistoryRecyclerItemBinding.bind(view)

        fun bind(dataHistoryWeather: Weather) = with(binding) {
            binding.tvCityNameHistory.text = dataHistoryWeather.city.name
            binding.tvCountryOfCityHistory.text = dataHistoryWeather.city.country
            binding.tvTemperature.text = "${dataHistoryWeather.temperature}°C"
            binding.tvCondition.text = dataHistoryWeather.condition

            Log.d("TAG", "dataHistoryWeather.icon = ${dataHistoryWeather.icon}")
            GlideToVectorYou.init().with(ivIconCondition.context).load(
                Uri.parse(dataHistoryWeather.icon), ivIconCondition)


/*
            GlideToVectorYou.init().with(imageCondition.context)
                .load(Uri.parse(weather.image), imageCondition);
*/

            //TODO setOnclick
        }
    }
}

// // inner --> to see "callback"
//    inner class WeatherViewHolder(binding: FragmentWeatherListRecyclerItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(weather: Weather) {
//            val binding = FragmentWeatherListRecyclerItemBinding.bind(itemView)
//            binding.tvCityName.text = weather.city.name
//            binding.tvCountryOfCity.text = weather.city.country
//            binding.root.setOnClickListener {
//                callback.onCityClick(weather)
//            }
//        }