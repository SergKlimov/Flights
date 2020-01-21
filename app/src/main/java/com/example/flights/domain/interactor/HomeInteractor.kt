package com.example.flights.domain.interactor

import com.example.flights.domain.model.Destination
import com.example.flights.domain.model.Route
import com.example.flights.domain.repository.DestinationRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val destinationRepository: DestinationRepository
) {

    fun observeRecentRoute() = Observable.combineLatest(
        destinationRepository.observeDeparture(),
        destinationRepository.observeArrival(),
        BiFunction<Destination, Destination, Route> { departure, arrival ->
            Route(departure, arrival)
        }
    )

}
