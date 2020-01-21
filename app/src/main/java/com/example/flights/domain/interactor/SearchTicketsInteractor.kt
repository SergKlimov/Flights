package com.example.flights.domain.interactor

import com.example.flights.domain.model.Destination
import com.example.flights.domain.model.Route
import com.example.flights.domain.repository.DestinationRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class SearchTicketsInteractor @Inject constructor(
    private val destinationRepository: DestinationRepository
) {

    fun getRoute() = Single.zip(
        destinationRepository.observeDeparture().firstOrError(),
        destinationRepository.observeArrival().firstOrError(),
        BiFunction<Destination, Destination, Route> { departure, arrival ->
            Route(departure, arrival)
        }
    )

}
