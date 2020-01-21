package com.example.flights.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    val city: String,
    val country: String,
    val fullname: String,
    val location: Location,
    val iata: List<String>
)
