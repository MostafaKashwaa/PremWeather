package com.example.premweather.repositories

import com.example.premweather.cache.WeatherDatabase
import com.example.premweather.cache.WeatherDatabaseServiceRoom
import com.example.premweather.domain.WeatherState
import com.example.premweather.network.OpenWeatherMapService
import com.example.premweather.network.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.RuntimeException

class WeatherRepositoryImpl(val database: WeatherDatabase) : WeatherRepository {
    private val weatherService = OpenWeatherMapService()
    private val dbService = WeatherDatabaseServiceRoom(database)

    override suspend fun getCurrentWeatherForCity(cityName: String, forceReload: Boolean): WeatherState {
        return if(forceReload) updateCurrentWeatherForCity(cityName) ?: throw RuntimeException("Unable to fetch data from any source")
        else withContext(Dispatchers.IO) {
            dbService.getCurrentWeather(cityName) ?: weatherService.getCurrentWeather(cityName).toDomain().also {
                dbService.insertCurrentWeather(it)
            }
        }
    }

    override suspend fun getWeatherForecast(cityName: String, forceReload: Boolean): List<WeatherState> {
        return if (forceReload) updateWeatherForecastForCity(cityName) ?: throw RuntimeException("Unable to fetch data from any source")
        else withContext(Dispatchers.IO) {
            dbService.getWeatherForecast(cityName) ?: getForecastByCityName(cityName).also {
                dbService.insertForecast(it)
            }
        }
    }

    private suspend fun updateWeatherForecastForCity(cityName: String): List<WeatherState>? {
        return withContext(Dispatchers.IO) {
            try {
                getForecastByCityName(cityName).also {
                    dbService.insertForecast(it)
                }
            } catch (e: Exception) {
                dbService.getWeatherForecast(cityName)
            }
        }
    }

    private suspend fun getForecastByCityName(cityName: String): List<WeatherState> {
        val city = weatherService.searchCities(cityName).first()
        return weatherService.getWeatherForecast(city.lat, city.lon).toDomain(city.toDomain())
    }

    private suspend fun updateCurrentWeatherForCity(cityName: String): WeatherState? {
        return withContext(Dispatchers.IO) {
            try {
                weatherService.getCurrentWeather(cityName).toDomain().also {
                    dbService.insertCurrentWeather(it)
                }
            } catch (e: Exception) {
                dbService.getCurrentWeather(cityName)
            }
        }
    }

}