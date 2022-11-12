package com.example.kotlin_gb.view.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentWeatherDetailsBinding
import com.example.kotlin_gb.model.Weather
import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.model.getWeatherIcon
import com.example.kotlin_gb.utils.WeatherLoader
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather_details.*
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

    @RequiresApi(Build.VERSION_CODES.O)
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
                                nowDate = weatherDTO.nowDt
                                humidity = weatherDTO.fact.humidity
                                windSpeed = weatherDTO.fact.windSpeed
                                pressure = weatherDTO.fact.pressureMm
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


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun renderData(weather: Weather) = with(binding) {
        imageConditionIcon.apply {
            setImageResource(getWeatherIcon(weather.condition))
            alpha = 0.7f
        }
        imageIconHumidity.apply {
            setImageResource(R.drawable.icon_humidity_png)
            alpha = 0.9f
        }
        imageIconWindSpeed.apply {
            setImageResource(R.drawable.icon_wind_png)
            alpha = 0.9f
        }
        imageIconPressure.apply {
            setImageResource(R.drawable.icon_pressure_png)
            alpha = 0.9f
        }

        tv_cityName.text = weather.city.name
        tv_cityCountry.text = weather.city.country
        tv_cityCoordinates.text = "${weather.city.lat.toString()} ${weather.city.lon.toString()}"
        tv_condition.text = weather.condition
        tv_temperatureValue.text = String.format("${weather.temperature}°")
        tv_feelsLikeValue.text = String.format("${weather.feelsLike}°")
        tv_dayOfWeek.text = formatDateStr(weather.nowDate)
        tv_humidityValue.text = String.format("${weather.humidity}%%")
        tv_windSpeedValue.text = String.format("${weather.windSpeed} m/sec")
        tv_pressureValue.text = String.format("${weather.pressure} mmHg")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateStr(strDate: String?): String? {
        return OffsetDateTime.parse(strDate)
            .format(DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH))
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
