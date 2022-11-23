package com.example.kotlin_gb.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentHistoryRecyclerItemBinding
import com.example.kotlin_gb.model.Weather

// Адаптер для RecyclerView абсолютно стандартный. В методе bind отображаем элемент списка
// через объединение данных и вешаем listener для наглядности. Метод setData добавляет данные для
// отображения. Этот метод создан неслучайно: так как данные грузятся из БД асинхронно, то
// RecyclerView и адаптер подготовятся гораздо раньше, чем появятся данные. Поэтому мы не можем
// передавать данные, например, в конструктор адаптера.

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecycleItemViewHolder>() {

    private var data: List<Weather> = arrayListOf()

    fun setData(data: List<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleItemViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_history_recycler_item, parent, false)
        return RecycleItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecycleItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecycleItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FragmentHistoryRecyclerItemBinding.bind(view)

        fun bind(data: Weather) = with(binding) {
            binding.tvCountryOfCityHistory.text = "${data.city.country}"
            binding.tvCityNameHistory.text = "${data.city.name}"

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