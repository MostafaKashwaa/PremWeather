package com.example.premweather.domain

data class WeatherState(
    var city: City,
    val date: Long? = 0L,
    val icon: String? = "10d",
    val temperature: Int? = 0,
    val minTemperature: Int? = 0,
    val maxTemperature: Int? = 0,
    val humidity: Int? = 0,
    val condition: String? = "Unknown",
    val description: String? = "Unknown",
    var probabilityOfPrecipitation: Double? = 0.0,
    var id: Long? = -1
)