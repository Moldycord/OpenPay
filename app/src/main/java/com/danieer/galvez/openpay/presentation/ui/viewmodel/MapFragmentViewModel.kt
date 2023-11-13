package com.danieer.galvez.openpay.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.danieer.galvez.openpay.domain.SaveLocationUseCase
import javax.inject.Inject

class MapFragmentViewModel @Inject constructor(
    private val saveLocationUseCase: SaveLocationUseCase
) : ViewModel() {

    fun saveLocationToFirestore(
        latitude: Double,
        longitude: Double,
        deviceId: String
    ) = saveLocationUseCase(
        latitude,
        longitude,
        deviceId
    )
}