package com.example.flights.presentation.utils.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.geometry.Point
import com.google.maps.android.projection.SphericalMercatorProjection

class GoogleProjection(private val worldWidth: Double = DEFAULT_WORLD_WIDTH) : Projection {

    companion object {
        private const val DEFAULT_WORLD_WIDTH = 1.0
    }

    private val projection = SphericalMercatorProjection(worldWidth)

    override fun toLatLng(point: Point): LatLng = projection.toLatLng(point)

    override fun toPoint(latLng: LatLng): Point = projection.toPoint(latLng)

    override fun getWorldWidth(): Double = worldWidth

}
