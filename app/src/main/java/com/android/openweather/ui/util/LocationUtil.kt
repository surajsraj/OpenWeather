package com.android.openweather.ui.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun PermissionRequest(
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current

    var locationPermissionsGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            locationPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                acc && isPermissionGranted
            }
        })

    LaunchedEffect(locationPermissionsGranted) {
        if (!locationPermissionsGranted) {
            locationPermissionLauncher.launch(locationPermissions)
        } else {
            onPermissionGranted.invoke()
        }
    }
}

fun getUserLocation(
    context: Context,
    onLocationResult: (location: Location?) -> Unit
) {
    val locationManager: LocationManager =
        (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        onLocationResult.invoke(locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER))
    }
}