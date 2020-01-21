package com.example.flights.presentation.di.component

import com.example.flights.presentation.ui.MainActivity
import com.example.flights.presentation.di.module.AppModule
import com.example.flights.presentation.di.module.RootNavigationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RootNavigationModule::class
    ]
)
interface AppComponent {

    fun inject(entry: MainActivity)

}
