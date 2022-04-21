package com.iliagoncharuk.simpleweatherapp.presentation.components

import android.content.Context
import android.location.LocationManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.iliagoncharuk.composebasics.ui.theme.ComposeBasicsTheme
import com.iliagoncharuk.simpleweatherapp.presentation.WeatherViewModel

@Composable
fun ShowProgressBar(){

    val locationManager = LocalContext.current.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    
    

    ComposeBasicsTheme() {
        Surface() {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                if(!gpsStatus){
                    Text(text = "Please turn on location and check your internet connection", modifier = Modifier.padding(24.dp), textAlign = TextAlign.Center)

                }

                CircularProgressIndicator()
            }


        }
    }

}