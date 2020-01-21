package com.example.flights.presentation.di.module

import android.content.Context
import com.example.flights.presentation.schedulers.SchedulersProvider
import com.example.flights.presentation.schedulers.SchedulersProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideSchedulers(): SchedulersProvider = SchedulersProviderImpl()

}
