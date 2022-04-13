package com.iliagoncharuk.simpleweatherapp.data.remote

import com.iliagoncharuk.simpleweatherapp.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query ("lat") latitude: String,
        @Query("lon") longitude:String,
        @Query("appid") appId:String = "0e0ebe2deb4e5c9b62c398d88749172e",
        @Query("units") units:String = "metric"
    ): WeatherDto



}