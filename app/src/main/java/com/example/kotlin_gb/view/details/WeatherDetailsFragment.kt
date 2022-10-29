package com.example.kotlin_gb.view.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.kotlin_gb.databinding.FragmentWeatherDetailsBinding
import com.example.kotlin_gb.model.Weather
import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.utils.getLines
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_weather_details.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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

        //val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA_WEATHER)
        val weather = arguments?.let { arg -> arg.getParcelable<Weather>(BUNDLE_EXTRA_WEATHER) }

        //if (weather != null) {renderData(weather)}
        //weather?.let { it -> renderData(it) }

        // А МОЖНО И ТАК, т.к. по сути переменная weather тут не нужна:
        // arguments?.let { arg -> arg.getParcelable<Weather>(BUNDLE_EXTRA_WEATHER) }?.let { renderData(it) }

        weather?.let {


            Thread {
                val uri =
                    URL("https://api.weather.yandex.ru/v2/informers?lat=${it.city.lat}&lon=${it.city.lon}")
                var myConnection: HttpsURLConnection? = null

                myConnection = uri.openConnection() as HttpsURLConnection
                myConnection.requestMethod = "GET"  // надо или нет???
                //myConnection.readTimeout = 5000   // надо или нет???
                myConnection.addRequestProperty(
                    "X-Yandex-API-Key",
                    "156100ef-9c46-47b4-abca-f3737c5cce72"
                )
                if (myConnection.responseCode == 200) {
                    //val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val inputSystem = myConnection.inputStream
                    val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                    val weatherDTO = Gson().fromJson(inputStreamReader, WeatherDTO::class.java)

                    //val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)

                    requireActivity().runOnUiThread {
                        renderData(it.apply {
                            feelsLike = weatherDTO.fact.feelsLike
                            temperature = weatherDTO.fact.temp
                        })
                    }

                    inputStreamReader.close()
                    inputSystem.close()

                } else {
                    binding.tvCityName.text = "FAILED TO CONNECTION"
                }
            }.start()
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
            /*val bundle = Bundle()
            bundle.putParcelable(BUNDLE_EXTRA_WEATHER, weather)
            val fr = WeatherDetailsFragment()
            fr.arguments = bundle
            return fr*/
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
