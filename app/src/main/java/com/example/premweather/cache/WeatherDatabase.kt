package com.example.premweather.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.premweather.cache.dao.CityDao
import com.example.premweather.cache.dao.WeatherStateDao
import com.example.premweather.cache.entities.CityEntity
import com.example.premweather.cache.entities.WeatherStateEntity

@Database(
    entities = [
        CityEntity::class,
        WeatherStateEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherStateDao(): WeatherStateDao
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context,
                            WeatherDatabase::class.java,
                            "weather.db"
                        ).build()
                    }
                }
            }
            return instance!!
        }
    }
}