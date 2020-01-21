package com.example.flights.presentation.di.module

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class RootNavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    internal fun provideRouter(): Router = cicerone.router

    @Provides
    @Singleton
    internal fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder

}
