package com.example.flights.data.repository

import android.content.SharedPreferences
import com.example.flights.domain.model.Destination
import com.example.flights.domain.model.Location
import com.example.flights.domain.repository.DestinationRepository
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

class DestinationRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val destinationConverter: Preference.Converter<Destination>
) : DestinationRepository {

    companion object {
        private const val DEPARTURE_KEY = "DEPARTURE_KEY"
        private const val ARRIVAL_KEY = "ARRIVAL_KEY"

        private val DEFAULT_DEPARTURE = Destination(
            city = "Санкт-Петербург",
            location = Location(lat = 59.7998772, lon = 30.2733421),
            iata = "LED"
        )
        private val DEFAULT_ARRIVAL = Destination(

            city = "Москва",
            location = Location(lat = 55.6, lon = 37.21667),
            iata = "VKO"
        )
    }

    private val rxSharedPreferences: RxSharedPreferences = RxSharedPreferences.create(sharedPreferences)

    private val departureObservable by lazy {
        rxSharedPreferences.getObject(
            DEPARTURE_KEY,
            DEFAULT_DEPARTURE, destinationConverter
        ).asObservable()
    }
    private val arrivalObservable by lazy {
        rxSharedPreferences.getObject(
            ARRIVAL_KEY,
            DEFAULT_ARRIVAL, destinationConverter
        ).asObservable()
    }

    override fun setDeparture(destination: Destination) {
        saveDestination(DEPARTURE_KEY, destination)
    }

    override fun setArrival(destination: Destination) {
        saveDestination(ARRIVAL_KEY, destination)
    }

    override fun observeDeparture(): Observable<Destination> = departureObservable

    override fun observeArrival(): Observable<Destination> = arrivalObservable

    private fun saveDestination(preferenceKey: String, destination: Destination) {
        sharedPreferences
            .edit()
            .putString(preferenceKey, destinationConverter.serialize(destination))
            .commit()
    }

}
