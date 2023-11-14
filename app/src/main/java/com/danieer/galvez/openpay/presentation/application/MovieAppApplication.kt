package com.danieer.galvez.openpay.presentation.application

import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.danieer.galvez.openpay.data.di.RoomModule
import com.danieer.galvez.openpay.domain.service.SaveUserLocationService
import com.danieer.galvez.openpay.presentation.di.DaggerAppComponent
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MovieAppApplication : Application(), HasAndroidInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
        FirebaseApp.initializeApp(this)

        val serviceIntent = Intent(this, SaveUserLocationService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Handler(Looper.getMainLooper()).post {
                ContextCompat.startForegroundService(this, serviceIntent)

            }
        } else {
            ContextCompat.startForegroundService(this, serviceIntent)
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingFragmentInjector
    }
}