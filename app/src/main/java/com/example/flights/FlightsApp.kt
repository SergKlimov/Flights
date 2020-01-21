package com.example.flights

import android.app.Application
import com.example.flights.presentation.di.component.AppComponent
import com.example.flights.presentation.di.component.DaggerAppComponent
import com.example.flights.presentation.di.module.AppModule

class FlightsApp : Application() {

    private val appComponentInternal: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

    fun getAppComponent(): AppComponent = appComponentInternal

}
