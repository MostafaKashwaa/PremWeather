package com.example.premweather.cache.dao

import androidx.room.*
import com.example.premweather.cache.entities.CityEntity
import com.example.premweather.cache.entities.CityForecastEntity
import com.example.premweather.cache.entities.WeatherStateEntity

@Dao
abstract class CityDao : BaseDao<CityEntity>() {

    @Query("SELECT * FROM cities WHERE city_id = :id")
    abstract suspend fun getCity(id: Long): CityEntity?;

    @Query("SELECT * FROM cities WHERE city_name = :cityName COLLATE NOCASE")
    abstract suspend fun findByCityName(cityName: String): CityEntity?

    @Query("SELECT * FROM cities WHERE city_name like '%'||:cityName||'%' COLLATE NOCASE")
    abstract suspend fun searchByCityName(cityName: String): CityEntity?

    @Query(value = "DELETE FROM cities WHERE city_name = :cityName COLLATE NOCASE")
    abstract suspend fun deleteByCityName(cityName: String)

    @Transaction
    @Query("SELECT * FROM cities WHERE city_name = :cityName COLLATE NOCASE")
    abstract suspend fun getCityForecast(cityName: String): CityForecastEntity?

    @Transaction
    @Query("SELECT * FROM cities WHERE city_name = :cityName COLLATE NOCASE")
    abstract suspend fun getCityCurrentWeather(cityName: String): CityForecastEntity?

    @Insert
    abstract suspend fun insertCity(city: CityEntity): Long

    @Insert
    abstract suspend fun insertForecast(forecast: List<WeatherStateEntity>)

    @Update
    abstract suspend fun updateCity(city: CityEntity)

}