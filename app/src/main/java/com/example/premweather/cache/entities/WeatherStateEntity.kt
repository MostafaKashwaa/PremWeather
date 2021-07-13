package com.example.premweather.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = WEATHER_STATES_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = [CITY_ID],
            childColumns = [WEATHER_STATE_FK_CITY],
            onDelete = ForeignKey.CASCADE

        )
    ]
)
data class WeatherStateEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WEATHER_STATE_ID)
    val id: Long = 0,
    val date: Long,
    val icon: String,
    val temperature: Int,
    @ColumnInfo(name = WEATHER_STATE_MIN_TEMP)
    val minTemperature: Int,
    @ColumnInfo(name = WEATHER_STATE_MAX_TEMP)
    val maxTemperature: Int,
    val humidity: Int,
    val condition: String,
    val description: String,
    @ColumnInfo(name = WEATHER_STATE_POP, defaultValue = "0.0")
    val probabilityOfPrecipitation: Double,
    @ColumnInfo(name = WEATHER_STATE_FK_CITY)
    var cityId: Long = 0
)