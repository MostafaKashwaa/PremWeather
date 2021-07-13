package com.example.premweather.cache.entities

import androidx.room.*

@Entity(tableName = CITIES_TABLE, indices = [Index(CITY_NAME)])
data class CityEntity(
    @ColumnInfo(name = CITY_NAME)
    val name: String,

    val lat: Double,

    val lon: Double,

    val country: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CITY_ID)
    var id: Long = 0,

    @Embedded val currentWeatherState: WeatherStateEntity? = null
)