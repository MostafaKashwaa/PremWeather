package com.example.premweather.domain

data class City(
    var name: String,
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    val country: String? = null,
    var id: Long = -1
)