package com.game.ag.data.repository

import com.game.ag.data.remote.WeatherAPI
import com.game.ag.domain.model.WeatherResponse
import com.game.ag.domain.repository.WeatherRepo

class WeatherRepoImpl(
    private val weatherAPI: WeatherAPI
) : WeatherRepo {

    override suspend fun getWeather(city: String): WeatherResponse =
        weatherAPI.getWeather(city = city)

}