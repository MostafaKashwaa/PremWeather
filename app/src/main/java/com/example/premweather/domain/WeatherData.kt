package com.example.premweather.domain

data class WeatherData(
    val city: City,
    val currentWeather: WeatherState?,
    val forecast: List<WeatherState?>
)