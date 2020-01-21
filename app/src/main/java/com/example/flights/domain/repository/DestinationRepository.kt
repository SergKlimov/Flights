package com.example.flights.domain.repository

import com.example.flights.domain.model.Destination
import io.reactivex.Observable

interface DestinationRepository {

    fun setDeparture(destination: Destination)

    fun setArrival(destination: Destination)

    fun observeDeparture(): Observable<Destination>

    fun observeArrival(): Observable<Destination>

}
