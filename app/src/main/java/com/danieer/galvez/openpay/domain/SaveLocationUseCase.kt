package com.danieer.galvez.openpay.domain

import com.danieer.galvez.openpay.data.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(latitude: Double, longitude: Double, deviceId: String): Task<Void> =
        firebaseRepository.saveLocationFirestore(latitude, longitude, deviceId)
}