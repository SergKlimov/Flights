package com.example.flights.presentation.di.component

import com.example.flights.presentation.ui.MainActivity
import com.example.flights.presentation.di.module.AppModule
import com.example.flights.presentation.di.module.NetworkModule
import com.example.flights.presentation.di.module.RepositoryModule
import com.example.flights.presentation.di.module.RootNavigationModule
import com.example.flights.presentation.di.module.StorageModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RootNavigationModule::class,
        NetworkModule::class,
        StorageModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(entry: MainActivity)

}
