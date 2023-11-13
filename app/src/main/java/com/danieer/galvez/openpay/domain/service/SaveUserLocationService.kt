package com.danieer.galvez.openpay.domain.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.danieer.galvez.openpay.utils.DeviceIdHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore

class SaveUserLocationService : Service() {

    private val fireStore = FirebaseFirestore.getInstance()
    override fun onBind(p0: Intent?): IBinder? {

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL.toLong()
            fastestInterval = FASTEST_UPDATE_INTERVAL.toLong()
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    saveLocationToFirestore(it.latitude, it.longitude)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

        return null
    }

    private fun saveLocationToFirestore(latitude: Double, longitude: Double) {
        val userLocation = hashMapOf(
            "latitude" to latitude, "longitude" to longitude
        )
        val deviceId = DeviceIdHelper.getDeviceId(this)

        fireStore.collection("service_locations").document(deviceId).set(userLocation)
            .addOnSuccessListener {

                Log.d("Location", "Location saved successfully")
            }.addOnFailureListener { e ->
                Log.e("Location", "Error saving location: $e")
            }
    }

    companion object {
        private const val LOCATION_UPDATE_INTERVAL = 300000
        private const val FASTEST_UPDATE_INTERVAL = 150000

    }
}