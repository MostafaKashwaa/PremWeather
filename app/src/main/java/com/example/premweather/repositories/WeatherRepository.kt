package com.example.premweather.repositories

import com.example.premweather.domain.WeatherState

interface WeatherRepository {
    suspend fun getCurrentWeatherForCity(cityName: String, forceReload: Boolean = false): WeatherState
    suspend fun getWeatherForecast(cityName: String, forceReload: Boolean = false): List<WeatherState>
}