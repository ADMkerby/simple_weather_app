package com.iliagoncharuk.simpleweatherapp.domain.repository

import com.iliagoncharuk.simpleweatherapp.data.remote.dto.WeatherDto
import com.iliagoncharuk.simpleweatherapp.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeather(longitude: String, latitude: String): WeatherDto
}