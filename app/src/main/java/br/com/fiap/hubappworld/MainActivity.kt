package br.com.fiap.hubappworld

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.hubappworld.screens.hub.HubScreen
import br.com.fiap.hubappworld.screens.hub.WeatherScreen
import br.com.fiap.hubappworld.ui.theme.HubAppWorldTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng

class MainActivity : ComponentActivity() {

    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private var locationRequired: Boolean = false

    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        enableEdgeToEdge()
        setContent {
            var currentLocation by remember {
                mutableStateOf(LatLng(0.toDouble(), 0.toDouble()))
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for (location in p0.locations) {
                        currentLocation = LatLng(location.latitude, location.longitude)
                    }
                }
            }

            HubAppWorldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        val launcherMultiplePermissions = rememberLauncherForActivityResult(
                            ActivityResultContracts.RequestMultiplePermissions()
                        ) { permissionMaps ->
                            val areGranted =
                                permissionMaps.values.reduce { acc, next -> acc && next }
                            if (areGranted) {
                                locationRequired = true
                                startLocationUpdates()
                                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                            }
                        }

                        Button(onClick = {
                            if (permissions.all {
                                    ContextCompat.checkSelfPermission(
                                        this@MainActivity,
                                        it
                                    ) == PackageManager.PERMISSION_GRANTED
                                }) {
                                startLocationUpdates()
                            } else {
                                launcherMultiplePermissions.launch(permissions)
                            }
                        }) {
                            Text(text = "Clique")
                        }

                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "hub") {
                            composable(route = "hub") {
                                HubScreen(navController)
                            }
                            composable(route = "clima") {
                                WeatherScreen(currentLocation)
                            }
                        }
                    }
                }
            }
        }
    }
}