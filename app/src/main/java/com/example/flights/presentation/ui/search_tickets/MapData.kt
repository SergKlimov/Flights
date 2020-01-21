package com.example.flights.presentation.ui.search_tickets

import com.example.flights.domain.model.Destination
import com.google.android.gms.maps.model.LatLng

data class MapData(
    val departure: Destination,
    val arrival: Destination,
    val controlPoints: BezierCurveControlPoints,
    val pathLatLng: List<LatLng>
)
