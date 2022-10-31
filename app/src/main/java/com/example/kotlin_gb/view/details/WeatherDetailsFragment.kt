package com.example.kotlin_gb.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlin_gb.databinding.FragmentWeatherDetailsBinding
import com.example.kotlin_gb.model.Weather
import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.utils.WeatherLoader
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather_details.*

class WeatherDetailsFragment : Fragment() {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding: FragmentWeatherDetailsBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.let { arg -> arg.getParcelable<Weather>(BUNDLE_EXTRA_WEATHER) }

        weather?.let { localWeather ->

            WeatherLoader.requestWeatherFromYandex(
                localWeather.city.lat,
                localWeather.city.lon,

                object : OnYandexWeatherResponse {
                    override fun onYandexWeatherResponse(weatherDTO: WeatherDTO) {
                        requireActivity().runOnUiThread {
                            renderData(localWeather.apply {
                                feelsLike = weatherDTO.fact.feelsLike
                                temperature = weatherDTO.fact.temp
                                condition = weatherDTO.fact.condition
                            })
                        }
                    }

                    override fun onFailedResponse() {
                        Snackbar.make(
                            binding.root,
                            "Failed to connect. ResponseCode is not 200",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(weather: Weather) = with(binding) {
        tv_cityName.text = weather.city.name
        tv_cityCoordinates.text = "${weather.city.lat.toString()} ${weather.city.lon.toString()}"
        tv_condition.text = weather.condition
        tv_temperatureValue.text = weather.temperature.toString()
        tv_feelsLikeValue.text = weather.feelsLike.toString()
    }

    companion object {
        private const val BUNDLE_EXTRA_WEATHER = "BUNDLE_EXTRA_WEATHER"
        fun newInstance(weather: Weather): WeatherDetailsFragment {

            return WeatherDetailsFragment().apply {
                this.arguments = Bundle().apply { putParcelable(BUNDLE_EXTRA_WEATHER, weather) }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
