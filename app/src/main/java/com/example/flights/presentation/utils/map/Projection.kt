package com.example.flights.presentation.utils.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.geometry.Point

interface Projection {

    fun toLatLng(point: Point): LatLng

    fun toPoint(latLng: LatLng): Point

    fun getWorldWidth(): Double

}
