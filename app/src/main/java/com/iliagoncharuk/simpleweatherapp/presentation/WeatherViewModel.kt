package com.iliagoncharuk.simpleweatherapp.presentation


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliagoncharuk.simpleweatherapp.common.Resource
import com.iliagoncharuk.simpleweatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
): ViewModel() {


    private val _state = mutableStateOf(WeatherState())
     val state: State<WeatherState> = _state

     fun getWeather(longitude: String, latitude: String) {
        getWeatherUseCase(longitude, latitude).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WeatherState(weather = result.data)

                }
                is Resource.Error -> {
                    _state.value = WeatherState(
                        error = result.message ?: "An unexpected error occured"
                    )


                }
                is Resource.Loading -> {
                    _state.value = WeatherState(isLoading = true)


                }
            }
        }.launchIn(viewModelScope)


    }
}