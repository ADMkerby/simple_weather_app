package com.iliagoncharuk.simpleweatherapp.presentation

import com.iliagoncharuk.simpleweatherapp.domain.model.Weather

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String = ""
) {
}