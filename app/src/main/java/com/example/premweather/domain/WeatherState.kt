package com.example.premweather.domain

data class WeatherState(
    var city: City,
    val date: Long,
    val icon: String,
    val temperature: Int,
    val minTemperature: Int,
    val maxTemperature: Int,
    val humidity: Int,
    val condition: String,
    val description: String,
    val probabilityOfPrecipitation: Double
)