package com.iliagoncharuk.simpleweatherapp.data.repository

import com.iliagoncharuk.simpleweatherapp.data.remote.OpenWeatherApi
import com.iliagoncharuk.simpleweatherapp.data.remote.dto.WeatherDto
import com.iliagoncharuk.simpleweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi
) : WeatherRepository {

    override suspend fun getWeather(longitude: String, latitude: String): WeatherDto {
        return api.getWeather(latitude, longitude)
    }


}