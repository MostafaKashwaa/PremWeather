package com.example.premweather.domain

data class City(
    val name: String,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val country: String? = null,
    var id: Long = -1
)