package com.example.premweather.cache.dao

import androidx.room.*
import com.example.premweather.cache.entities.WeatherStateEntity

@Dao
abstract class WeatherStateDao : BaseDao<WeatherStateEntity>() {
    @Query("SELECT * FROM weather_states WHERE state_id = :id")
    abstract suspend fun getState(id: Long): WeatherStateEntity

    @Query("DELETE FROM weather_states WHERE weather_state_city_id = :cityId")
    abstract suspend fun deleteForCity(cityId: Long)
}