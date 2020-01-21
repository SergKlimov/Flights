package com.example.flights.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(val lat: Double, val lon: Double)
