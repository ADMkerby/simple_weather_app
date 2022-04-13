package com.iliagoncharuk.simpleweatherapp.domain.model

import com.iliagoncharuk.simpleweatherapp.data.remote.dto.*
import com.iliagoncharuk.simpleweatherapp.data.remote.dto.WeatherDescription

data class Weather(
    val cloudiness: Clouds,
    val date: Int,
    val metricData: MetricData,
    val cityName: String,
    val timezone: Int,
    val weatherDescription: List<WeatherDescription>,
    val wind: Wind
){







}
