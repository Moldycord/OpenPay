package com.danieer.galvez.openpay.presentation.di

import com.danieer.galvez.openpay.presentation.di.scopes.FragmentScope
import com.danieer.galvez.openpay.presentation.ui.fragments.MapFragment
import com.danieer.galvez.openpay.presentation.ui.fragments.MoviesFragment
import com.danieer.galvez.openpay.presentation.ui.fragments.UploadImageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindFragmentMovies(): MoviesFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindFragmentMap(): MapFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindFragmentUploadImage(): UploadImageFragment

}