package com.example.flights.presentation.di.module

import android.content.Context
import android.content.SharedPreferences
import com.example.flights.data.converter.DestinationConverter
import com.example.flights.domain.model.Destination
import com.f2prateek.rx.preferences2.Preference
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    companion object {
        private const val PREFERENCES_NAME = "FlightsPreferences"
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideDestinationConverter(moshi: Moshi): Preference.Converter<Destination> =
        DestinationConverter(moshi.adapter(Destination::class.java))

}
