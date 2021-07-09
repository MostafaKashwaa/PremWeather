package com.example.premweather.network

import com.example.premweather.appid
import com.example.premweather.network.dto.CityDTO
import com.example.premweather.network.dto.CurrentWeatherDTO
import com.example.premweather.network.dto.DailyWeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/data/2.5/onecall?units=metric&exclude=minutely,hourly,alerts,current&appid=$appid")
    suspend fun fetchDailyWeatherByCoords(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): DailyWeatherDTO

    @GET("/geo/1.0/direct?limit=5&appid=$appid")
    suspend fun searchCitiesByName(
        @Query("q") city: String
    ): List<CityDTO>

    @GET("/data/2.5/weather?units=metric&appid=$appid")
    suspend fun fetchCurrentWeather(
        @Query("q") city: String
    ): CurrentWeatherDTO
}