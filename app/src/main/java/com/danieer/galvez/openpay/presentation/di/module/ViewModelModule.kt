package com.danieer.galvez.openpay.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danieer.galvez.openpay.presentation.di.annotations.ViewModelKey
import com.danieer.galvez.openpay.presentation.di.factory.ViewModelFactory
import com.danieer.galvez.openpay.presentation.ui.viewmodel.MapFragmentViewModel
import com.danieer.galvez.openpay.presentation.ui.viewmodel.MovieSearchViewModel
import com.danieer.galvez.openpay.presentation.ui.viewmodel.MoviesFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchViewModel::class)
    abstract fun bindHomeViewModel(viewModel: MovieSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoviesFragmentViewModel::class)
    abstract fun bindMoviesFragmentViewModel(viewModel: MoviesFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapFragmentViewModel::class)
    abstract fun bindMapFragmentViewModel(viewModel: MapFragmentViewModel): ViewModel
}