package com.example.premweather.network.dto

data class CurrentWeatherDTO(
    val dt: Long,
    val name: String,
    val weather: List<WeatherDTO>,
    val main: WeatherMainDTO,
    val sys: WeatherSysDTO
)

data class WeatherSysDTO(
    val country: String
)

data class WeatherMainDTO(
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val humidity: Int
)