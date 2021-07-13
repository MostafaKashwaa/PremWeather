package com.example.premweather.ui

import androidx.lifecycle.*
import com.example.premweather.cache.WeatherDatabase
import com.example.premweather.domain.WeatherState
import com.example.premweather.repositories.WeatherRepositoryImpl
import kotlinx.coroutines.launch

sealed class Status(val message: String) {
    object Loading : Status("loading data. Please, wait..")
    object Success : Status("done")
    object Failed : Status("loading failed :(")
    object Idle : Status("Select a city to see weather condition")
}

class WeatherViewModel(
    private val database: WeatherDatabase
) : ViewModel() {
    private val repository = WeatherRepositoryImpl(database)

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
                repository.getCurrentWeatherForCity(cityName, forceReload = true)
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
                repository.getWeatherForecast(cityName, forceReload = true)
            }.onSuccess {
                _forecast.postValue(it)
                _status.postValue(Status.Success)
            }.onFailure {
                _status.postValue(Status.Failed)
            }
        }
    }

}

class WeatherViewModelFactory(
    private val database: WeatherDatabase
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(database) as T
    }
}