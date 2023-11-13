package com.danieer.galvez.openpay.domain.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.danieer.galvez.openpay.R
import com.danieer.galvez.openpay.presentation.ui.activity.HomeActivity
import com.danieer.galvez.openpay.utils.DeviceIdHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore

class SaveUserLocationService : Service() {


    private val fireStore = FirebaseFirestore.getInstance()

    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "service_channel"
    private val CHANNEl_NAME = "Servicio de ubicación"

    override

    fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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

        startForeground(NOTIFICATION_ID, buildNotification())

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
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {

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

    private fun buildNotification(): Notification {
        createNotificationChannel()

        val intent = Intent(this, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.map_service_title))
            .setContentText(getString(R.string.map_service_description))
            .setSmallIcon(R.drawable.ic_map)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEl_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    companion object {
        private const val LOCATION_UPDATE_INTERVAL = 300000
        private const val FASTEST_UPDATE_INTERVAL = 150000

    }
}