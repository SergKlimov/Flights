package com.example.flights.data.network.response

import com.example.flights.domain.model.City
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CitiesResponse(val cities: List<City>)
