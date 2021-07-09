package com.example.premweather.network

import com.example.premweather.network.dto.CityDTO
import com.example.premweather.network.dto.CurrentWeatherDTO
import com.example.premweather.network.dto.DailyWeatherDTO
import com.example.premweather.openWeatherMapUrl
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
}