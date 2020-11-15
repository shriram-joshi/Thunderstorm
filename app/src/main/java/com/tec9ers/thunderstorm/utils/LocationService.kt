package com.tec9ers.thunderstorm.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat

class LocationService : Service() {
    private lateinit var listener: LocationListener
    lateinit var locationManager: LocationManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val intent = Intent("location_update")
                intent.putExtra("coordinates lat", location.latitude)
                intent.putExtra("coordinates lon", location.longitude)
                Toast.makeText(this@LocationService, "Broadcast Sent", Toast.LENGTH_SHORT).show()
                sendBroadcast(intent)
            }

            @SuppressLint("MissingPermission")
            override fun onProviderEnabled(provider: String) {
               val location = locationManager.getLastKnownLocation(provider)
                val intent = Intent("location_update")
                intent.putExtra("coordinates lat", location?.latitude)
                intent.putExtra("coordinates lon", location?.longitude)
                Toast.makeText(this@LocationService, "Broadcast Sent", Toast.LENGTH_SHORT).show()
                sendBroadcast(intent)
            }

            override fun onProviderDisabled(provider: String) {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
        locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            return
        }
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 3000, 0f, listener).also { Toast.makeText(this, "Permission Accepted", Toast.LENGTH_SHORT).show() }

    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeUpdates(listener)
    }
}
