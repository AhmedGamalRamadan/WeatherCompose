package com.game.ag.weathercompose.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.ag.domain.repository.WeatherRepo
import com.game.ag.weathercompose.util.WeatherAttributeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(500) // Adjust debounce period as necessary
                .collect { query ->
                    if (query.isNotEmpty()) {
                        getWeather(query)
                    }
                }
        }
    }

    private val _weatherAttributeState = MutableStateFlow<WeatherAttributeState?>(null)
    val weatherAttributeState = _weatherAttributeState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun getWeather(city: String) {

        try {
            viewModelScope.launch {
                val response = weatherRepo.getWeather(city = city)
                _weatherAttributeState.value = WeatherAttributeState.Success(response)
            }
        } catch (e: Exception) {
            _weatherAttributeState.value = WeatherAttributeState.Error("Unexpected Error: ${e.message}")
        }

    }
}