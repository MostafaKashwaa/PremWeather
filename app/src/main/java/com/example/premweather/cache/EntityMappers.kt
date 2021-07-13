package com.example.premweather.cache

import com.example.premweather.cache.entities.CityEntity
import com.example.premweather.cache.entities.CityForecastEntity
import com.example.premweather.cache.entities.WeatherStateEntity
import com.example.premweather.domain.City
import com.example.premweather.domain.WeatherData
import com.example.premweather.domain.WeatherState

fun CityEntity.toDomain(): City {
    return City(
        this.name,
        this.lat,
        this.lon,
        this.country,
        this.id
    )
}

fun cityEntityFromDomain(city: City): CityEntity {
    return CityEntity(
        id = 0,
        name = city.name,
        lat = city.lat,
        lon = city.lon,
        country = city.country?: "Unknown"
    )
}

fun CityEntity.toDomainWeatherState(): WeatherState {
    return WeatherState(
        City(this.name, this.lat, this.lon, this.country, this.id),
        this.currentWeatherState?.date ,
        this.currentWeatherState?.icon,
        this.currentWeatherState?.temperature,
        this.currentWeatherState?.minTemperature,
        this.currentWeatherState?.maxTemperature,
        this.currentWeatherState?.humidity,
        this.currentWeatherState?.condition,
        this.currentWeatherState?.description,
        this.currentWeatherState?.probabilityOfPrecipitation,
        this.currentWeatherState?.id
    )
}

fun cityEntityFromDomain(weatherState: WeatherState): CityEntity {
    return CityEntity(
        weatherState.city.id,
        weatherState.city.name,
        weatherState.city.lat,
        weatherState.city.lon,
        weatherState.city.country!!,
        WeatherStateEntity(
            weatherState.id?:0,
            weatherState.date?:0L,
            weatherState.icon?: "10d",
            weatherState.temperature?: 0,
            weatherState.minTemperature?: 0,
            weatherState.maxTemperature?: 0,
            weatherState.humidity?: 0,
            weatherState.condition?: "Clouds",
            weatherState.description?: "few clouds",
            weatherState.probabilityOfPrecipitation?: 0.0
        )
    )
}

fun WeatherStateEntity.toDomain(city: City): WeatherState {
    return WeatherState(
        city,
        this.date,
        this.icon,
        this.temperature,
        this.minTemperature,
        this.maxTemperature,
        this.humidity,
        this.condition,
        this.description,
        this.probabilityOfPrecipitation,
        this.id
    )
}

fun weatherStateEntityFromDomain(state: WeatherState): WeatherStateEntity {
    return WeatherStateEntity(
        date = state.date?: 0L,
        icon = state.icon?: "10d",
        temperature = state.temperature?: 0,
        minTemperature = state.minTemperature?: 0,
        maxTemperature = state.maxTemperature?: 0,
        humidity = state.humidity?: 0,
        condition = state.condition?: "clouds",
        description = state.description?: "few clouds",
        probabilityOfPrecipitation = state.probabilityOfPrecipitation?: 0.0,
    )
}

fun List<WeatherState>.toEntityList(): List<WeatherStateEntity> {
    return this.map {
        weatherStateEntityFromDomain(it)
    }
}

fun CityForecastEntity.toDomainStatesList(): List<WeatherState> {
    return this.weatherForecast.map {
        it.toDomain(this.city.toDomain())
    }
}

fun CityForecastEntity.toDomainWeatherData(): WeatherData {
    return WeatherData(
        this.city.toDomain(),
        this.city.currentWeatherState?.toDomain(this.city.toDomain()),
        this.toDomainStatesList()
    )
}