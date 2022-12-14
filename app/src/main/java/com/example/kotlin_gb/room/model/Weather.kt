package com.example.kotlin_gb.room.model

//import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.example.kotlin_gb.room.model.dto.WeatherDTO
import kotlinx.parcelize.Parcelize

//     id 'kotlin-android-extensions'   <-- for Parcelize in gradle

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    var temperature: Int = 10,
    var feelsLike: Int = 11,
    var condition: String = "not information",
    var nowDate: String = "not information",
    var humidity: Int = 0,
    var windSpeed: Double = 0.0,
    var pressure: Int = 0,
    val icon: String = "bkn_n"
) : Parcelable

@Parcelize
data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String = "Russia"
) : Parcelable

fun convertWeatherDTOtoWeather(weatherDTO: WeatherDTO): Weather {
    return Weather(
        temperature = weatherDTO.fact.temp,
        feelsLike = weatherDTO.fact.feelsLike,
        condition = weatherDTO.fact.condition,
        nowDate = weatherDTO.nowDt,
        humidity = weatherDTO.fact.humidity,
        windSpeed = weatherDTO.fact.windSpeed,
        pressure = weatherDTO.fact.pressureMm,
        icon = weatherDTO.fact.icon
    )
}

// НЕИЗМЕНЯЕМЫЙ СПИСОК!!! listOf
fun getRussianCities(): List<Weather> {
    return listOf(
        Weather(City("Volgograd", 48.7080, 44.5133), 25, -10),
        Weather(City("Москва", 55.755826, 37.617299900000035), 1, 2),
        Weather(City("Санкт-Петербург", 59.9342802, 30.335098600000038), 3, 3),
        Weather(City("Новосибирск", 55.00835259999999, 82.93573270000002), 5, 6),
        Weather(
            City("Екатеринбург", 56.83892609999999, 60.60570250000001), 7,
            8
        ),
        Weather(City("Нижний Новгород", 56.2965039, 43.936059), 9, 10),
        Weather(City("Казань", 55.8304307, 49.06608060000008), 11, 12),
        Weather(City("Челябинск", 55.1644419, 61.4368432), 13, 14),
        Weather(City("Омск", 54.9884804, 73.32423610000001), 15, 16),
        Weather(City("Ростов-на-Дону", 47.2357137, 39.701505), 17, 18),
        Weather(City("Уфа", 54.7387621, 55.972055400000045), 19, 20)
    )
}

fun getWorldCities(): List<Weather> {
    return listOf(
        Weather(City("Лондон", 51.5085300, -0.1257400, "Great Britain"), 1, 2),
        Weather(City("Токио", 35.6895000, 139.6917100, "Japan"), 3, 4),
        Weather(City("Париж", 48.8534100, 2.3488000, "France"), 5, 6),
        Weather(City("Берлин", 52.52000659999999, 13.404953999999975, "Germany"), 7, 8),
        Weather(City("Рим", 41.9027835, 12.496365500000024, "Italy"), 9, 10),
        Weather(City("Минск", 53.90453979999999, 27.561524400000053, "Belarus"), 11, 12),
        Weather(City("Стамбул", 41.0082376, 28.97835889999999, "Turkey"), 13, 14),
        Weather(City("Вашингтон", 38.9071923, -77.03687070000001, "USA"), 15, 16),
        Weather(City("Киев", 50.4501, 30.523400000000038, "Ukraine"), 17, 18),
        Weather(City("Пекин", 39.90419989999999, 116.40739630000007, "China"), 19, 20)
    )
}

fun getDefaultCity(): City {
    return City("Moscow", 55.755826, 37.617299900000035)
}