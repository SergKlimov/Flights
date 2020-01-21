package com.example.flights.presentation.di.module

import androidx.lifecycle.ViewModel
import com.example.flights.presentation.di.ViewModelKey
import com.example.flights.presentation.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindMainViewModel(viewModel: HomeViewModel): ViewModel

}
