package com.game.ag.weathercompose.di

import com.game.ag.data.remote.WeatherAPI
import com.game.ag.data.repository.WeatherRepoImpl
import com.game.ag.domain.repository.WeatherRepo
import com.game.ag.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideRetrofit(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRepo(weatherAPI: WeatherAPI):WeatherRepo{
        return WeatherRepoImpl(weatherAPI)
    }

}