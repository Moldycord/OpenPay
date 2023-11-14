package com.danieer.galvez.openpay.domain

import android.net.Uri
import com.danieer.galvez.openpay.data.repository.StorageFirebaseRepository
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val storageFirebaseRepository: StorageFirebaseRepository
) {

    operator fun invoke(filePath: Uri): UploadTask =
        storageFirebaseRepository.uploadToFirebase(filePath)
}