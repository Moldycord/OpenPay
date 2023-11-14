package com.danieer.galvez.openpay.data.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.UUID
import javax.inject.Inject

class StorageFirebaseRepository @Inject constructor() {

    private val storage = FirebaseStorage.getInstance()
    private val firebaseStorage = storage.reference

    fun uploadToFirebase(filePath: Uri): UploadTask {
        val storageReferenceFile = firebaseStorage.child("images/" + UUID.randomUUID().toString())
        return storageReferenceFile.putFile(filePath)
    }

}