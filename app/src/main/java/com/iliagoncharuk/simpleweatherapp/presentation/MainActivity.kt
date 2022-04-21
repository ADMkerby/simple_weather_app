package com.iliagoncharuk.simpleweatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.gun0912.tedpermission.PermissionListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel:WeatherViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        viewModel.updateLocation()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            WeatherScreen(viewModel = viewModel,permissionListener)
        }
    }


    private var permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            viewModel.updateLocation()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
        }

    }




}
