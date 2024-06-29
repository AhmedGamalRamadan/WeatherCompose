package com.game.ag.domain.repository

import com.game.ag.domain.model.WeatherResponse

interface WeatherRepo {

    suspend fun getWeather(
        city:String
    ): WeatherResponse

}