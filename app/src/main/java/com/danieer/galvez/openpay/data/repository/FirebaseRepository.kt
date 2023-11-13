package com.danieer.galvez.openpay.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    private val fireStore = FirebaseFirestore.getInstance()
    fun saveLocationFirestore(latitude: Double, longitude: Double, deviceId: String): Task<Void> {
        val locationMap = hashMapOf(
            LATITUDE to latitude, LONGITUDE to longitude
        )
        return fireStore.collection(USER_LOCATION_COLLECTION_NAME).document(deviceId)
            .set(locationMap)
    }

    companion object {
        private const val USER_LOCATION_COLLECTION_NAME = "user_location"
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
    }
}