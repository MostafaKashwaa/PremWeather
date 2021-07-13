package com.example.premweather.cache.entities

import androidx.room.*

data class CityForecastEntity(
    @Embedded val city: CityEntity,
    @Relation(
        entity = WeatherStateEntity::class,
        parentColumn = CITY_ID,
        entityColumn = WEATHER_STATE_FK_CITY
    )
    val weatherForecast: List<WeatherStateEntity>
)