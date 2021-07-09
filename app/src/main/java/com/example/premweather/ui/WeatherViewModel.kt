package com.example.premweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premweather.domain.WeatherState
import com.example.premweather.repositories.WeatherRepository
import kotlinx.coroutines.launch

sealed class Status(val message: String) {
    object Loading : Status("loading data. Please, wait..")
    object Success : Status("done")
    object Failed : Status("loading failed :(")
    object Idle : Status("Select a city to see weather condition")
}

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private var _status: MutableLiveData<Status> = MutableLiveData(Status.Idle)
    val status: LiveData<Status>
        get() = _status

    private var _weatherState = MutableLiveData<WeatherState>()
    val weatherState: LiveData<WeatherState>
        get() = _weatherState

    private var _forecast = MutableLiveData<List<WeatherState>>()
    val forecast: LiveData<List<WeatherState>>
        get() = _forecast

    fun loadWeatherForCity(cityName: String) {
        viewModelScope.launch {
            _status.value = Status.Loading
            kotlin.runCatching {
                repository.getCurrentWeatherForCity(cityName)
            }.onSuccess {
                _weatherState.postValue(it)
                _status.postValue(Status.Success)
            }.onFailure {
                _status.postValue(Status.Failed)
            }
        }
    }

    fun loadForecast(cityName: String) {
        viewModelScope.launch {
            _status.value = Status.Loading
            kotlin.runCatching {
                repository.getWeatherForecast(cityName)
            }.onSuccess {
                _forecast.postValue(it)
                _status.postValue(Status.Success)
            }.onFailure {
                _status.postValue(Status.Failed)
            }
        }
    }
}