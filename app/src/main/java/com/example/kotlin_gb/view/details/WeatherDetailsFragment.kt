package com.example.kotlin_gb.view.details

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlin_gb.BuildConfig
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentWeatherDetailsBinding
import com.example.kotlin_gb.model.Weather
import com.example.kotlin_gb.model.dto.FactDTO
import com.example.kotlin_gb.model.dto.WeatherDTO
import com.example.kotlin_gb.model.getWeatherIcon
import com.example.kotlin_gb.service.DetailService
import com.example.kotlin_gb.utils.Const
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_CONDITION_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_DATA_EMPTY_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_FEELS_LIKE_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_HUMIDITY_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_INTENT_EMPTY_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_INTENT_FILTER
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_LOAD_RESULT_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_NOW_DT_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_PRESSURE_MM_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_REQUEST_ERROR_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_REQUEST_ERROR_MESSAGE_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_RESPONSE_EMPTY_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_RESPONSE_SUCCESS_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_TEMP_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_URL_MALFORMED_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.DETAILS_WIND_SPEED_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.FEELS_LIKE_INVALID
import com.example.kotlin_gb.utils.Const.Companion.HTTPS_YANDEX_URL
import com.example.kotlin_gb.utils.Const.Companion.HUMIDITY_INVALID
import com.example.kotlin_gb.utils.Const.Companion.LATITUDE_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.LONGITUDE_EXTRA
import com.example.kotlin_gb.utils.Const.Companion.PRESSURE_MM_INVALID
import com.example.kotlin_gb.utils.Const.Companion.PROCESS_ERROR
import com.example.kotlin_gb.utils.Const.Companion.TEMP_INVALID
import com.example.kotlin_gb.utils.Const.Companion.WIND_SPEED_INVALID
import com.example.kotlin_gb.utils.Const.Companion.X_YANDEX_API_KEY
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_weather_details.*
import okhttp3.*
import java.io.IOException
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

    // Creating receiver and receive data in it
    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> errorRequest(intent)
                //DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderDateWeatherDTO(
                    WeatherDTO(
                        FactDTO(
                            temp = intent.getIntExtra(DETAILS_TEMP_EXTRA, TEMP_INVALID),
                            feelsLike = intent.getIntExtra(
                                DETAILS_FEELS_LIKE_EXTRA,
                                FEELS_LIKE_INVALID
                            ),
                            condition = intent.getStringExtra(DETAILS_CONDITION_EXTRA)!!,
                            humidity = intent.getIntExtra(DETAILS_HUMIDITY_EXTRA, HUMIDITY_INVALID),
                            pressureMm = intent.getIntExtra(
                                DETAILS_PRESSURE_MM_EXTRA,
                                PRESSURE_MM_INVALID
                            ),
                            windSpeed = intent.getDoubleExtra(
                                DETAILS_WIND_SPEED_EXTRA,
                                WIND_SPEED_INVALID
                            )
                        ),
                        nowDt = intent.getStringExtra(DETAILS_NOW_DT_EXTRA)!!
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(layoutInflater, container, false)
        // REGISTERING Receiver:
        requireActivity().let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA_WEATHER) ?: Weather()
        getWeather()
    }

    private fun getWeather() = with(binding) {
        clWeatherDetails.hide()
        flLoadingLayout.show()
        /*requireActivity().let {
            it.startService(Intent(it, DetailService::class.java).apply {
                putExtra(LATITUDE_EXTRA, weatherBundle.city.lat)
                putExtra(LONGITUDE_EXTRA, weatherBundle.city.lon)
            })
        }*/
        val client = OkHttpClient() // create client
        val builder: Request.Builder = Request.Builder() // create builder of request
        builder.header(X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY) // header of our request
        builder.url("${HTTPS_YANDEX_URL}?lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}") // create URL
        val request: Request = builder.build() // create request
        val call: Call = client.newCall(request) // move request in queue and send

        call.enqueue(object : Callback {
            //Все запросы становятся в очередь, и для каждого вызывается callback с двумя методами, которые
            //надо обработать: onResponse и onFailure. Внутри мы создаём знакомый нам Handler и
            //обрабатываем ответы от сервера. Если ответ от сервера пришёл, убеждаемся, что ответ успешен и
            //непустой, и используем библиотеку Gson для преобразования данных в подходящую нам модель. Не
            //забываем использовать Handler или аналог, так как ответ всё ещё приходит в рабочем потоке, а не в
            //основном.
            val handler: Handler = Handler()

            // if response is come from server
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call, response: Response) {
                val serverResponse = response.body()?.string()
                if (serverResponse != null && response.isSuccessful) {
                    handler.post {
                        val weatherDTO: WeatherDTO =
                            Gson().fromJson(serverResponse, WeatherDTO::class.java)
                        renderDateWeatherDTO(weatherDTO)
                    }
                } else {
                    TODO(PROCESS_ERROR)
                }
            }

            // Вызывается при сбое в процессе запроса на сервер (на нашей стороне)
            override fun onFailure(call: Call, e: IOException) {
                TODO(PROCESS_ERROR)
            }
        })


    }

    // WAY with broadcast
    @RequiresApi(Build.VERSION_CODES.O)
    fun renderDateWeatherDTO(weatherDTO: WeatherDTO) = with(binding) {
        clWeatherDetails.show()
        flLoadingLayout.hide()

        if (weatherDTO.fact.temp == TEMP_INVALID || weatherDTO.fact.feelsLike == FEELS_LIKE_INVALID
            || weatherDTO.fact.humidity == HUMIDITY_INVALID || weatherDTO.fact.pressureMm == PRESSURE_MM_INVALID
            || weatherDTO.fact.windSpeed == WIND_SPEED_INVALID
        ) {
            TODO(PROCESS_ERROR)
        } else {
            val localWeather = weatherBundle.apply {
                temperature = weatherDTO.fact.temp
                feelsLike = weatherDTO.fact.feelsLike
                condition = weatherDTO.fact.condition
                humidity = weatherDTO.fact.humidity
                windSpeed = weatherDTO.fact.windSpeed
                pressure = weatherDTO.fact.pressureMm
                nowDate = weatherDTO.nowDt
            }
            renderData(localWeather)
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
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
        // TODO round coordinates!!!
        tv_cityCoordinates.text = "lat:${weather.city.lat}, lon:${weather.city.lon}"
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

    private fun errorRequest(intent: Intent) {
        val errorNsg = intent.getStringExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA)
        Toast.makeText(requireActivity(), "REQUEST_ERROR: $errorNsg", Toast.LENGTH_LONG).show()
    }

    private fun View.show() {
        this.visibility = View.VISIBLE
    }

    private fun View.hide() {
        this.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        requireActivity().let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
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
