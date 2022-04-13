package com.iliagoncharuk.simpleweatherapp.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.iliagoncharuk.simpleweatherapp.domain.model.Weather

data class WeatherDto(
    @SerializedName("base")
    val base: String,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val metricData: MetricData,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weatherDescription: List<WeatherDescription>,
    @SerializedName("wind")
    val wind: Wind
)

fun WeatherDto.toWeather(): Weather {
    return Weather(
        cloudiness = clouds,
        date = dt,
        metricData = metricData,
        cityName = name,
        timezone = timezone,
        weatherDescription = weatherDescription,
        wind = wind

    )


}