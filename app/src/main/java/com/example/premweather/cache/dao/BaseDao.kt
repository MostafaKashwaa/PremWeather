package com.example.premweather.cache.dao

import androidx.room.*
import com.example.premweather.cache.entities.CityEntity

abstract class BaseDao<T> {
    @Insert
    abstract suspend fun insert(entities: List<T>): List<Long>

    @Update
    abstract suspend fun update(vararg entities: T)

    @Delete
    abstract suspend fun delete(vararg entities: T)

}