package com.example.flights.presentation.di.component

import com.example.flights.presentation.ui.MainActivity
import com.example.flights.presentation.di.module.AppModule
import com.example.flights.presentation.di.module.NetworkModule
import com.example.flights.presentation.di.module.RepositoryModule
import com.example.flights.presentation.di.module.RootNavigationModule
import com.example.flights.presentation.di.module.StorageModule
import com.example.flights.presentation.di.module.ViewModelModule
import com.example.flights.presentation.utils.view_model.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RootNavigationModule::class,
        NetworkModule::class,
        StorageModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(entry: MainActivity)

    fun provideViewModelFactory(): ViewModelFactory

}
