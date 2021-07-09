package com.example.premweather.network.dto

data class DailyWeatherDTO(
    val daily: List<DayForecast>
) {
    data class DayForecast(
        val dt: Long,
        val temp: Temperature,
        val humidity: Int,
        val weather: List<WeatherDTO>,
        val pop: Double
    )

    data class Temperature(
        val min: Double,
        val max: Double
    )
}