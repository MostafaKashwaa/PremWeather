package com.example.premweather.repositories

import android.util.Log
import com.example.premweather.cache.WeatherDatabase
import com.example.premweather.cache.WeatherDatabaseServiceRoom
import com.example.premweather.cache.entities.WeatherStateEntity
import com.example.premweather.domain.WeatherData
import com.example.premweather.domain.WeatherState
import com.example.premweather.network.OpenWeatherMapService
import com.example.premweather.network.dto.CityDTO
import com.example.premweather.network.dto.CurrentWeatherDTO
import com.example.premweather.network.toDomain
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class WeatherRepository(val database: WeatherDatabase) {
    private val weatherService = OpenWeatherMapService()
    private val dbService = WeatherDatabaseServiceRoom(database)

    suspend fun getCurrentWeatherForCity(cityName: String): WeatherState {

        return withContext(Dispatchers.IO) {
            weatherService.getCurrentWeather(cityName).toDomain()
        }
    }

    suspend fun updateCurrentWeatherForCity(cityName: String): WeatherState {
        return withContext(Dispatchers.IO) {
            weatherService.getCurrentWeather(cityName).toDomain().also {
                dbService.insertCurrentWeather(it)
            }
        }
    }

    suspend fun getWeatherForecast(cityName: String): List<WeatherState> {
        return withContext(Dispatchers.IO) {
            val city = weatherService.searchCities(cityName).first()
            weatherService.getWeatherForecast(city.lat, city.lon).toDomain(city.toDomain())
        }
    }

    suspend fun getWeatherData(cityName: String): WeatherData {
        return withContext(Dispatchers.IO) {
            weatherService.getWeatherData(cityName)
        }
    }
}