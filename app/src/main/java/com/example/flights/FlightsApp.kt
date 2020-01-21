package com.example.flights

import android.app.Application
import com.example.flights.presentation.di.component.AppComponent
import com.example.flights.presentation.di.component.DaggerAppComponent
import com.example.flights.presentation.di.module.AppModule
import com.example.flights.presentation.di.module.NetworkModule
import com.example.flights.presentation.utils.view_model.ViewModelFactory
import com.example.flights.presentation.utils.view_model.ViewModelFactoryProvider

class FlightsApp : Application(), ViewModelFactoryProvider {

    private val appComponentInternal: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .networkModule(NetworkModule(BuildConfig.API_URL))
            .build()
    }

    override fun getViewModelFactory(): ViewModelFactory {
        return appComponentInternal.provideViewModelFactory()
    }

    fun getAppComponent(): AppComponent = appComponentInternal

}
