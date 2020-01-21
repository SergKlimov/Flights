package com.example.flights.presentation.di.module

import com.example.flights.data.repository.DestinationRepositoryImpl
import com.example.flights.domain.repository.DestinationRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideDestinationRepository(repository: DestinationRepositoryImpl): DestinationRepository

}
