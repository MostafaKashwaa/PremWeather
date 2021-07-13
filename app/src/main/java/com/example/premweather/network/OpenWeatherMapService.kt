package com.example.premweather.network

import com.example.premweather.domain.WeatherData
import com.example.premweather.network.dto.CityDTO
import com.example.premweather.network.dto.CurrentWeatherDTO
import com.example.premweather.network.dto.DailyWeatherDTO
import com.example.premweather.openWeatherMapUrl
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherMapService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(openWeatherMapUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: WeatherAPI = retrofit.create(WeatherAPI::class.java)

    suspend fun searchCities(name: String): List<CityDTO> {
        return service.searchCitiesByName(name)
    }

    suspend fun getCurrentWeather(city: String): CurrentWeatherDTO {
        return service.fetchCurrentWeather(city)
    }

    suspend fun getWeatherForecast(lat: Double, lon: Double): DailyWeatherDTO {
        return service.fetchDailyWeatherByCoords(lat, lon)
    }

    suspend fun getWeatherData(cityName: String) : WeatherData {
        return coroutineScope {
            val currentWeather = async {
                getCurrentWeather(cityName).toDomain()
            }

            val city = searchCities(cityName).first()
            val weatherForecast = async {
                getWeatherForecast(city.lat, city.lon).toDomain(city.toDomain())
            }

            val forecast = weatherForecast.await()
            val current = currentWeather.await()
            forecast.forEach { it.city.name = current.city.name }
            current.city.lat = city.lat
            current.city.lon = city.lon
            current.probabilityOfPrecipitation = forecast.first().probabilityOfPrecipitation

            return@coroutineScope WeatherData(
                city.toDomain(),
                current,
                forecast
            )
        }
    }
}