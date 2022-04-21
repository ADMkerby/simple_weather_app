package com.iliagoncharuk.simpleweatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.provider.TedPermissionProvider
import com.iliagoncharuk.composebasics.ui.theme.ComposeBasicsTheme
import com.iliagoncharuk.simpleweatherapp.common.PermissonRequest
import com.iliagoncharuk.simpleweatherapp.presentation.components.ShowProgressBar
import com.iliagoncharuk.simpleweatherapp.presentation.components.StartButtonView


@Composable
    fun WeatherScreen(viewModel: WeatherViewModel, permissionListener: PermissionListener){

        var shouldShowButton by rememberSaveable { mutableStateOf(true) }

        if(shouldShowButton){
            StartButtonView (onContinueClicked =
            {
                PermissonRequest.requestLocationPermissions(permissionListener)
                if (ActivityCompat.checkSelfPermission(
                        TedPermissionProvider.context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        TedPermissionProvider.context,
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
                        if (viewModel.state.value.weather != null){

                            Text("Weather in", style = MaterialTheme.typography.h6)

                            viewModel.state.value.weather?.let { Text(it.cityName, style = MaterialTheme.typography.h6) }

                            Spacer(modifier = Modifier.height(20.dp))

                            viewModel.state.value.weather?.weatherDescription?.get(0)?.let { Text(it.main) }

                            Text("${viewModel.state.value.weather?.metricData?.temp}°")
                            
                        }else{
                            ShowProgressBar()
                        }

                    }

                }
            }

        }


    }



