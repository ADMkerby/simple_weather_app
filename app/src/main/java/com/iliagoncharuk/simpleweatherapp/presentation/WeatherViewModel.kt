package com.iliagoncharuk.simpleweatherapp.presentation


import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import com.iliagoncharuk.simpleweatherapp.common.Resource
import com.iliagoncharuk.simpleweatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    application: Application,
    private val getWeatherUseCase: GetWeatherUseCase
): AndroidViewModel(application) {

    private  var fusedLocationClient: FusedLocationProviderClient
    private  var locationCallback: LocationCallback




    private  var locationRequest: LocationRequest = LocationRequest.create().apply {

        interval = 30000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = 100
    }

    init {

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                getLastKnownLocation()
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    }


    private val _state = mutableStateOf(WeatherState())
     val state: State<WeatherState> = _state

     private fun getWeather(longitude: String, latitude: String) {
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


    fun getLastKnownLocation() {

        var latitude: Double
        var longitude: Double


        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                latitude = location.latitude
            } else {
                latitude = 0.0
            }

            if (location != null) {
                longitude = location.longitude
            } else {
                longitude = 0.0
            }

            getWeather(longitude.toString(), latitude.toString())



        }

    }

    fun updateLocation(){

        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }



}