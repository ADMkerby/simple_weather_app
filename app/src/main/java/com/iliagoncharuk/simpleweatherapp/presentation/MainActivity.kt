package com.iliagoncharuk.simpleweatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.iliagoncharuk.composebasics.ui.theme.ComposeBasicsTheme
import com.iliagoncharuk.simpleweatherapp.common.PermissonRequest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    val viewModel:WeatherViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        updateLocation()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MainView(viewModel = viewModel,permissionListener)
        }

        locationRequest = LocationRequest.create().apply {

            interval = 30000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                getLastKnownLocation()
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }


    private var permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            updateLocation()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {

        }
    }

    fun getLastKnownLocation() {

        var latitude: Double
        var longitude: Double


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
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

            viewModel.getWeather(longitude.toString(), latitude.toString())



        }

    }

    fun updateLocation(){

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
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

@Composable
fun MainView(viewModel: WeatherViewModel, permissionListener:PermissionListener){

    var shouldShowButton by rememberSaveable { mutableStateOf(true) }

    if(shouldShowButton){
        StartButtonView (onContinueClicked =
        {
            PermissonRequest.requestLocationPermissions(permissionListener)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {


                shouldShowButton = false
            }
            })
    } else{

        ComposeBasicsTheme() {
            Surface {
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Weather in", style = MaterialTheme.typography.h6)
                    viewModel.state.value.weather?.let { Text(it.cityName, style = MaterialTheme.typography.h6) }
                    Spacer(modifier = Modifier.height(20.dp))
                    viewModel.state.value.weather?.weatherDescription?.get(0)?.let { Text(it.main) }
                    Text("${viewModel.state.value.weather?.metricData?.temp}°")
                }

            }
        }

    }


}

@Composable
fun StartButtonView(onContinueClicked: () -> Unit){
    ComposeBasicsTheme() {
       Surface() {
           Column(modifier = Modifier.fillMaxSize(),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally) {
               Button(onClick =onContinueClicked) {
                   Text(text = "Check weather")
               }
           }


        }
    }
}