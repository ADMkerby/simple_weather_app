package com.iliagoncharuk.simpleweatherapp.common

import android.Manifest
import android.os.Build
import com.google.android.gms.location.LocationRequest
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

object PermissonRequest {

    fun requestLocationPermissions(permissionListener: PermissionListener) {


        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION
            )
            .check()


    }
}