package com.iliagoncharuk.simpleweatherapp.domain.usecase

import com.iliagoncharuk.simpleweatherapp.common.Resource
import com.iliagoncharuk.simpleweatherapp.data.remote.dto.toWeather
import com.iliagoncharuk.simpleweatherapp.domain.model.Weather
import com.iliagoncharuk.simpleweatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(longitude: String, latitude: String): Flow<Resource<Weather>> = flow {

        try {
            emit(Resource.Loading<Weather>())
            val weather = repository.getWeather(longitude, latitude).toWeather()
            emit(Resource.Success<Weather>(weather))
        } catch(e: HttpException) {
            emit(Resource.Error<Weather>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<Weather>("Couldn't reach server. Check your internet connection."))
        }
    }


    }