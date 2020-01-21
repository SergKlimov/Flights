package com.example.flights.presentation.utils.map

import android.animation.TypeEvaluator
import com.example.flights.presentation.ui.search_tickets.BezierCurveControlPoints
import com.google.maps.android.geometry.Point

class CubicCurveEvaluator(private val controlPoints: BezierCurveControlPoints) : TypeEvaluator<Point> {

    override fun evaluate(fraction: Float, startValue: Point, endValue: Point): Point = pointInCubicCurve(
        controlPoints.first,
        controlPoints.second,
        controlPoints.third,
        controlPoints.fourth,
        fraction.toDouble()
    )

    fun evaluate(fraction: Float): Point {
        return evaluate(fraction, controlPoints.first, controlPoints.fourth)
    }

}
