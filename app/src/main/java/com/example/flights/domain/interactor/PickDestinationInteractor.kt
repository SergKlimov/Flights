package com.example.flights.domain.interactor

import com.example.flights.domain.model.City
import com.example.flights.domain.model.Destination
import com.example.flights.domain.repository.CityRepository
import com.example.flights.domain.repository.DestinationRepository
import io.reactivex.Completable
import javax.inject.Inject

class PickDestinationInteractor @Inject constructor(
    private val cityRepository: CityRepository,
    private val destinationRepository: DestinationRepository
) {

    fun findDestinations(request: String) = cityRepository.getCities(request)
        .map(this::convertToDestinationList)

    fun saveDestination(isDeparture: Boolean, destination: Destination): Completable = Completable.fromAction {
        if (isDeparture) {
            destinationRepository.setDeparture(destination)
        } else {
            destinationRepository.setArrival(destination)
        }
    }

    private fun convertToDestinationList(cities: List<City>): List<Destination> = cities.map { city ->
        if (city.iata.isNotEmpty()) {
            city.iata.distinct().map { iata ->
                Destination(city.city, city.fullname, city.location, iata)
            }
        } else {
            listOf(Destination(city.city, city.fullname, city.location))
        }
    }.flatten()

}
