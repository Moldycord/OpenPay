package com.danieer.galvez.openpay.presentation.di

import com.danieer.galvez.openpay.presentation.ui.activity.HomeActivity
import com.danieer.galvez.openpay.presentation.ui.activity.MovieDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector
    abstract fun bindHomeSearchActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun bindMovieDetailActivity(): MovieDetailActivity
}