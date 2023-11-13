package com.danieer.galvez.openpay.utils

import android.content.Context
import android.provider.Settings


object DeviceIdHelper {

    fun getDeviceId(context: Context): String {
        val androidID =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return androidID ?: DEFAULT_DEVICE_ID
    }
}