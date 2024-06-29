package com.game.ag.data.remote

import com.game.ag.data.util.Constants
import com.game.ag.domain.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key")apiKey:String=Constants.API_KEY,
        @Query("q")city:String,
        @Query("aqi")aqi:String="no"
    ):WeatherResponse


}
//=>http://api.weatherapi.com/v1/ current.json? key=3174617eb6864a9a8ca235046240205 & q=Cairo&aqi=no