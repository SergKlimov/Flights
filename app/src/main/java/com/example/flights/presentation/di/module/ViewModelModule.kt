package com.example.flights.presentation.di.module

import androidx.lifecycle.ViewModel
import com.example.flights.presentation.di.ViewModelKey
import com.example.flights.presentation.ui.home.HomeViewModel
import com.example.flights.presentation.ui.pick_destination.PickDestinationViewModel
import com.example.flights.presentation.ui.search_tickets.SearchTicketsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PickDestinationViewModel::class)
    fun bindPickDestinationViewModel(viewModel: PickDestinationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchTicketsViewModel::class)
    fun bindSearchTicketsViewModel(viewModel: SearchTicketsViewModel): ViewModel

}
