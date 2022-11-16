package com.example.kotlin_gb.model

import com.example.kotlin_gb.R

enum class WeatherCondition(val condition:String) {
    CLEAR("clear"),
    PARTLY_CLOUDY("partly-cloudy"),
    CLOUDY("cloudy"),
    OVERCAST("overcast"),
    DRIZZLE("drizzle"),
    LIGHT_RAIN("light-rain"),
    RAIN("rain"),
    MODERATE_RAIN("moderate-rain"),
    HEAVY_RAIN("heavy-rain"),
    CONTINUOUS_HEAVY_RAIN("continuous-heavy-rain"),
    SHOWERS("showers"),
    WET_SNOW("wet-snow"),
    LIGHT_SNOW("light-snow"),
    SNOW("snow"),
    SNOW_SHOWERS("snow-showers"),
    HAIL("hail"),
    THUNDERSTORM("thunderstorm"),
    THUNDERSTORM_WITH_RAIN("thunderstorm-with-rain"),
    THUNDERSTORM_WITH_HAIL("thunderstorm-with-hail"),
}

fun getWeatherIcon(condition: String) : Int {
    return when(condition) {
        WeatherCondition.CLEAR.condition -> R.drawable.condition_clear
        WeatherCondition.PARTLY_CLOUDY.condition -> R.drawable.condition_partly_cloudy
        WeatherCondition.CLOUDY.condition -> R.drawable.condition_cloudy
        WeatherCondition.OVERCAST.condition -> R.drawable.condition_overcast
        WeatherCondition.DRIZZLE.condition -> R.drawable.condition_drizzle
        WeatherCondition.LIGHT_RAIN.condition -> R.drawable.condition_light_rain
        WeatherCondition.RAIN.condition -> R.drawable.condition_rain
        WeatherCondition.MODERATE_RAIN.condition -> R.drawable.condition_moderate_rain
        WeatherCondition.HEAVY_RAIN.condition -> R.drawable.condition_heavy_rain
        WeatherCondition.CONTINUOUS_HEAVY_RAIN.condition -> R.drawable.condition_continuous_heavy_rain
        WeatherCondition.SHOWERS.condition -> R.drawable.condition_showers
        WeatherCondition.WET_SNOW.condition -> R.drawable.condition_wet_snow
        WeatherCondition.LIGHT_SNOW.condition -> R.drawable.condition_light_snow
        WeatherCondition.SNOW.condition -> R.drawable.condition_snow
        WeatherCondition.SNOW_SHOWERS.condition -> R.drawable.condition_snow_showers
        WeatherCondition.HAIL.condition -> R.drawable.condition_hail
        WeatherCondition.THUNDERSTORM.condition -> R.drawable.condition_thunderstorm
        WeatherCondition.THUNDERSTORM_WITH_RAIN.condition -> R.drawable.condition_thunderstorm_with_rain
        WeatherCondition.THUNDERSTORM_WITH_HAIL.condition -> R.drawable.condition_thunderstorm_with_hail
      else -> R.drawable.condition_none
    }

}