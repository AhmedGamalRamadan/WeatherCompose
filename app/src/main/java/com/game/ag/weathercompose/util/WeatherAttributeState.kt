package com.game.ag.weathercompose.util

import com.game.ag.domain.model.WeatherResponse

sealed class WeatherAttributeState {
    data class Success(val response: WeatherResponse) : WeatherAttributeState()
    data class Error(val message: String) : WeatherAttributeState()
    object Loading:WeatherAttributeState()
}