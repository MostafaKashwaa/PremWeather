package com.example.premweather.repositories

import com.example.premweather.domain.WeatherState
import com.example.premweather.network.OpenWeatherMapService
import com.example.premweather.network.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class WeatherRepository {
    private val weatherService = OpenWeatherMapService()

    suspend fun getCurrentWeatherForCity(cityName: String): WeatherState {
        return withContext(Dispatchers.IO) {
            weatherService.getCurrentWeather(cityName).toDomain()
        }
    }

    suspend fun getWeatherForecast(cityName: String): List<WeatherState> {
        return withContext(Dispatchers.IO) {
            val city = weatherService.searchCities(cityName).first()
            weatherService.getWeatherForecast(city.lat, city.lon).toDomain(city.toDomain())
        }
    }
}