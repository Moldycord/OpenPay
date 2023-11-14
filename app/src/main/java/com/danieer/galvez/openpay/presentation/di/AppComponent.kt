package com.danieer.galvez.openpay.presentation.di

import android.app.Application
import com.danieer.galvez.openpay.data.di.NetworkModule
import com.danieer.galvez.openpay.data.di.RoomModule
import com.danieer.galvez.openpay.presentation.application.MovieAppApplication
import com.danieer.galvez.openpay.presentation.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class,
        ViewModelModule::class,
        NetworkModule::class,
        RoomModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(androidApp: MovieAppApplication)
}