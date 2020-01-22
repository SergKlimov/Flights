package com.example.flights.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Destination(
    val city: String,
    val fullName: String = "",
    val location: Location,
    val iata: String
)
