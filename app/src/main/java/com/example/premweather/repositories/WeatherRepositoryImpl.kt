package com.example.premweather.repositories

import com.example.premweather.cache.WeatherDatabase
import com.example.premweather.cache.WeatherDatabaseServiceRoom
import com.example.premweather.domain.WeatherState
import com.example.premweather.network.OpenWeatherMapService
import com.example.premweather.network.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(val database: WeatherDatabase) : WeatherRepository {
    private val weatherService = OpenWeatherMapService()
    private val dbService = WeatherDatabaseServiceRoom(database)

    override suspend fun getCurrentWeatherForCity(cityName: String, forceReload: Boolean): WeatherState {
        if(forceReload) return updateCurrentWeatherForCity(cityName)
        return withContext(Dispatchers.IO) {
            dbService.getCurrentWeather(cityName)?: weatherService.getCurrentWeather(cityName).toDomain().also {
                dbService.insertCurrentWeather(it)
            }
        }
    }

    override suspend fun getWeatherForecast(cityName: String, forceReload: Boolean): List<WeatherState> {
        if (forceReload) return withContext(Dispatchers.IO) {
            getForecastByCityName(cityName).also {
                dbService.insertForecast(it)
            }
        }
        return withContext(Dispatchers.IO) {
            dbService.getWeatherForecast(cityName)?: getForecastByCityName(cityName).also {
                dbService.insertForecast(it)
            }
        }
    }

    private suspend fun getForecastByCityName(cityName: String): List<WeatherState> {
        val city = weatherService.searchCities(cityName).first()
        return weatherService.getWeatherForecast(city.lat, city.lon).toDomain(city.toDomain())
    }

    private suspend fun updateCurrentWeatherForCity(cityName: String): WeatherState {
        return withContext(Dispatchers.IO) {
            weatherService.getCurrentWeather(cityName).toDomain().also {
                dbService.insertCurrentWeather(it)
            }
        }
    }
}