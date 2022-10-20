package com.example.kotlin_gb.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentWeatherListBinding
import com.example.kotlin_gb.model.Weather
import kotlinx.android.synthetic.main.fragment_weather_details.*

class WeatherDetailsFragment : Fragment() {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA_WEATHER)
        if (weather != null) {
            renderData(weather)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(weather: Weather) = with(binding) {
        tv_cityName.text = weather.city.name
        tv_cityCoordinates.text = "${weather.city.lat.toString()} ${weather.city.lon.toString()}"
        tv_temperatureValue.text = weather.temperature.toString()
        tv_feelsLikeValue.text = weather.feelsLike.toString()
    }

    companion object {
        private const val BUNDLE_EXTRA_WEATHER = "BUNDLE_EXTRA_WEATHER"
        fun newInstance(weather: Weather): WeatherDetailsFragment {
            val bundle: Bundle = Bundle()
            bundle.putParcelable(BUNDLE_EXTRA_WEATHER, weather)
            val fr = WeatherDetailsFragment()
            fr.arguments = bundle
            return fr
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
