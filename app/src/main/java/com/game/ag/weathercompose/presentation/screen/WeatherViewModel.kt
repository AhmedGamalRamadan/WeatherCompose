package com.game.ag.weathercompose.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.ag.domain.model.WeatherResponse
import com.game.ag.domain.repository.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    private val _weatherAttributeState = MutableStateFlow<WeatherResponse?>(null)
    val weatherAttributeState = _weatherAttributeState.asStateFlow()

    fun getWeather(city: String) {

        try {
            viewModelScope.launch {
                val response = weatherRepo.getWeather(city = city)
                _weatherAttributeState.value = response
            }
        } catch (e: Exception) {
            Log.d("Unexpected Error:", e.message.toString())

        }

    }
}