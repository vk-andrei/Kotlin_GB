package com.example.kotlin_gb.view.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.bumptech.glide.Glide
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentWeatherDetailsBinding
import com.example.kotlin_gb.model.City
import com.example.kotlin_gb.model.Weather
import com.example.kotlin_gb.model.getWeatherIcon
import com.example.kotlin_gb.utils.Utils.hide
import com.example.kotlin_gb.utils.Utils.show
import com.example.kotlin_gb.utils.Utils.showSnackErrorWithAction
import com.example.kotlin_gb.viewmodel.AppState
import com.example.kotlin_gb.viewmodel.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_weather_details.*
import java.io.File
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WeatherDetailsFragment : Fragment() {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding: FragmentWeatherDetailsBinding
        get() {
            return _binding!!
        }

    private lateinit var weatherBundle: Weather
    private lateinit var city: City

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA_WEATHER) ?: Weather()
        city = weatherBundle.city

        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer { it -> renderData(it) })
        viewModel.getWeatherFromRemoteSource(city.lat, city.lon)
        //TODO if answer too long --> Toast ERROR
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.clWeatherDetails.hide()
                binding.flLoadingLayout.hide()
                binding.clMainView.showSnackErrorWithAction(
                    getString(R.string.snack_bar_error_title),
                    Snackbar.LENGTH_INDEFINITE,
                    getString(R.string.snack_bar_reload_title)
                ) { viewModel.getWeatherFromRemoteSource(city.lat, city.lon) }
            }
            is AppState.Loading -> {
                binding.clWeatherDetails.hide()
                binding.flLoadingLayout.show()
            }
            is AppState.SuccessMultiWeather -> TODO()
            is AppState.SuccessSingleWeather -> {
                binding.clWeatherDetails.show()
                binding.flLoadingLayout.hide()
                setWeather(appState.weather)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setWeather(weather: Weather) = with(binding) {
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

        tv_cityName.text = city.name
        tv_cityCountry.text = city.country
        // TODO round coordinates!!!
        tv_cityCoordinates.text = "lat:${city.lat}, lon:${city.lon}"
        tv_condition.text = weather.condition

        tv_temperatureValue.text = String.format("${weather.temperature}°")
        tv_feelsLikeValue.text = String.format("${weather.feelsLike}°")
        tv_dayOfWeek.text = formatDateStr(weather.nowDate)
        tv_humidityValue.text = String.format("${weather.humidity}%%")
        tv_windSpeedValue.text = String.format("${weather.windSpeed} m/sec")
        tv_pressureValue.text = String.format("${weather.pressure} mmHg")

        //Glide.with(requireActivity()).load("https://freepngimg.com/thumb/city/36275-3-city-hd.png").into(imgHeader)

        //Picasso.get().load("https://freepngimg.com/thumb/city/36275-3-city-hd.png").into(imgHeader)

        // Coil:
        imgHeader.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
        //imgHeader.load(R.drawable......)
        //imgHeader.load(File("/path/to/image.jpg"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateStr(strDate: String?): String? {
        return OffsetDateTime.parse(strDate)
            .format(DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val BUNDLE_EXTRA_WEATHER = "BUNDLE_EXTRA_WEATHER"
        fun newInstance(weather: Weather): WeatherDetailsFragment {

            return WeatherDetailsFragment().apply {
                this.arguments = Bundle().apply { putParcelable(BUNDLE_EXTRA_WEATHER, weather) }
            }
        }
    }
}
