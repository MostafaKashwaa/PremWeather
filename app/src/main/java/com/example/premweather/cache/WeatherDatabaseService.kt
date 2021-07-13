package com.example.premweather.cache

import com.example.premweather.cache.entities.WeatherStateEntity
import com.example.premweather.domain.City
import com.example.premweather.domain.WeatherState

interface WeatherDatabaseService {
    suspend fun getCity(id: Long): City?
    suspend fun getCityByName(cityName: String): City?
    suspend fun insertCity(city: City): Long
    suspend fun insertForecast(forecast: List<WeatherState>): List<Long>
    suspend fun insertCurrentWeather(weatherState: WeatherState): Long
    suspend fun getCurrentWeather(cityName: String): WeatherState?
    suspend fun getWeatherForecast(cityName: String): List<WeatherState>?
}

class WeatherDatabaseServiceRoom(private val db: WeatherDatabase) : WeatherDatabaseService {

    override suspend fun insertCity(city: City): Long {
        return db.cityDao().insertCity(
            cityEntityFromDomain(city)
        )
    }

    override suspend fun getCityByName(cityName: String): City? {
        return db.cityDao().findByCityName(cityName)?.toDomain()
    }

    override suspend fun getCity(id: Long): City? {
        return db.cityDao().getCity(id)?.toDomain()
    }

    override suspend fun insertCurrentWeather(weatherState: WeatherState): Long {

        getCityByName(weatherState.city.name)?.let {
            weatherState.city = it
            val entity = cityEntityFromDomain(weatherState)
            entity.id = it.id
            db.cityDao().updateCity(entity)
            return it.id
        }

        return db.cityDao().insertCity(
            cityEntityFromDomain(weatherState)
        )
    }

    override suspend fun getCurrentWeather(cityName: String): WeatherState? {
        return db.cityDao().findByCityName(cityName)?.toDomainWeatherState()
    }

    override suspend fun insertForecast(forecast: List<WeatherState>): List<Long> {
        val forecastEntities = forecast.toEntityList()
        getCityByName(forecast.first().city.name)?.let {
            db.weatherStateDao().deleteForCity(it.id)
            return insertForecastEntities(forecastEntities, it.id)
        }

        val cityId = insertCity(forecast.first().city)
        return insertForecastEntities(forecastEntities, cityId)
    }


    override suspend fun getWeatherForecast(cityName: String): List<WeatherState>? {
        return db.cityDao().getCityForecast(cityName)?.toDomainStatesList()
    }


    private suspend fun insertForecastEntities(
        forecastEntities: List<WeatherStateEntity>,
        cityId: Long
    ): List<Long> {
        forecastEntities.forEach { forecastEntity ->
            forecastEntity.cityId = cityId
        }
        return db.weatherStateDao().insert(forecastEntities)
    }

}