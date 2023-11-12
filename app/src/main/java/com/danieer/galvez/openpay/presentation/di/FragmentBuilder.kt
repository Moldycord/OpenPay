package com.danieer.galvez.openpay.presentation.di

import com.danieer.galvez.openpay.presentation.di.scopes.FragmentScope
import com.danieer.galvez.openpay.presentation.ui.fragments.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindFragmentMovies(): MoviesFragment

}