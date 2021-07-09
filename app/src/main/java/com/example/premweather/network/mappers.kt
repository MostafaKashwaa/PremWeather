package com.example.premweather.network

import com.example.premweather.domain.City
import com.example.premweather.domain.WeatherState
import com.example.premweather.network.dto.CityDTO
import com.example.premweather.network.dto.CurrentWeatherDTO
import com.example.premweather.network.dto.DailyWeatherDTO

fun CityDTO.toDomain(): City {
    return City(this.name, this.lat, this.lon, this.country)
}

fun CurrentWeatherDTO.toDomain(): WeatherState {
    return WeatherState(
        City(this.name, country = this.sys.country),
        this.dt,
        this.weather.first().icon,
        this.main.temp.toInt(),
        this.main.temp_min.toInt(),
        this.main.temp_max.toInt(),
        this.main.humidity,
        this.weather.first().main,
        this.weather.first().description,
        0.0
    )
}

fun DailyWeatherDTO.toDomain(city: City): List<WeatherState> {
    return this.daily.map {
        WeatherState(
            city,
            it.dt,
            it.weather.first().icon,
            0,
            it.temp.min.toInt(),
            it.temp.max.toInt(),
            it.humidity,
            it.weather.first().main,
            it.weather.first().description,
            it.pop
        )
    }
}