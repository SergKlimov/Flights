package com.example.flights.data.repository

import com.example.flights.domain.repository.CityRepository
import com.example.flights.data.network.api.CitiesApi
import com.example.flights.data.network.response.CitiesResponse
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val citiesApi: CitiesApi
) : CityRepository {

    override fun getCities(query: String) = citiesApi.getCities(query)
        .map(CitiesResponse::cities)

}
