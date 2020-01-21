package com.example.flights.domain.repository

import com.example.flights.domain.model.City
import io.reactivex.Single

interface CityRepository {

    fun getCities(query: String): Single<List<City>>

}
