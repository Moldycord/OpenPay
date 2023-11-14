package com.danieer.galvez.openpay.presentation.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.danieer.galvez.openpay.domain.UploadImageUseCase
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class UploadImageFragmentViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    fun uploadImage(filePath: Uri): UploadTask = uploadImageUseCase(filePath)

}