package com.example.flights.presentation.ui.search_tickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flights.domain.interactor.SearchTicketsInteractor
import com.example.flights.domain.model.Route
import com.example.flights.presentation.base.BaseViewModel
import com.example.flights.presentation.schedulers.SchedulersProvider
import com.example.flights.presentation.utils.map.Projection
import com.example.flights.presentation.utils.map.pointInCubicCurve
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.geometry.Point
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SearchTicketsViewModel @Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val searchTicketsInteractor: SearchTicketsInteractor
) : BaseViewModel() {

    companion object {
        private const val CONTROL_POINT_ANGLE_OFFSET = 90
    }

    private val mapData = MutableLiveData<MapData>()

    fun getMapDataLiveData(): LiveData<MapData> = mapData

    fun onMapReady(projection: Projection) {

        if (mapData.value != null) return

        searchTicketsInteractor.getRoute()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
            .map { mapToData(projection, it) }
            .subscribe(mapData::setValue, errorEventInternal::setValue)
            .untilDestroy()
    }

    private fun mapToData(projection: Projection, route: Route): MapData {
        val departure = route.departure.location
        val arrival = route.arrival.location
        val startLatLng = LatLng(departure.lat, departure.lon)
        val endLatLng = LatLng(arrival.lat, arrival.lon)

        val (controlPoints, path) = calculateCubicPath(projection, startLatLng, endLatLng)

        return MapData(route.departure, route.arrival, controlPoints, path)
    }

    /*
    *
    * if projection.WorldWidth == 1:
    *
    * (0,0)                 (1,0)
    *   +---------------------+---------------->
    *   |                     |
    *   |                     |
    *   |   A              B  |   A1(A.x+1, A.y)
    *   |   *<------------>*<---->*
    *   |       dxOrigin      dxInverted
    *   |                     |
    *   |                     |
    *   v                     v
    *
    * */
    private fun calculateCubicPath(
        projection: Projection,
        startPosition: LatLng,
        endPosition: LatLng
    ): Pair<BezierCurveControlPoints, List<LatLng>> {
        val worldWidth = projection.getWorldWidth()
        var A = projection.toPoint(startPosition).run { Point(x, y) }
        var B = projection.toPoint(endPosition).run { Point(x, y) }

        val dxOrigin = A.x - B.x
        val dxInverted = if (dxOrigin != 0.0) {
            dxOrigin - (dxOrigin / dxOrigin.absoluteValue) * worldWidth
        } else {
            dxOrigin
        }

        val dx: Double
        val dy = A.y - B.y
        if (dxOrigin.absoluteValue > dxInverted.absoluteValue) {
            val leftPoint = minOf(A, B, compareBy(Point::x))
            val A1 = Point(leftPoint.x + worldWidth, leftPoint.y)

            if (dxInverted > 0) A = A1 else B = A1
            dx = dxInverted
        } else {
            dx = dxOrigin
        }

        val (secondControlPoint, thirdControlPoint) = getIntermediateControlPoints(A, dx, dy)
        val controlPoints = BezierCurveControlPoints(A, secondControlPoint, thirdControlPoint, B)

        val curve = mutableListOf<LatLng>()
        var fraction = 0.0

        while (fraction < 1) {
            val point = pointInCubicCurve(A, secondControlPoint, thirdControlPoint, B, fraction)
            curve.add(projection.toLatLng(point))
            fraction += 0.01
        }

        curve.add(projection.toLatLng(B))

        return controlPoints to curve
    }

    private fun getIntermediateControlPoints(
        startPoint: Point,
        dx: Double,
        dy: Double
    ): Pair<Point, Point> {
        val dxHalf = dx * 0.5
        val dyHalf = dy * 0.5

        val midPoint = Point(startPoint.x - dxHalf, startPoint.y - dyHalf)

        val r = sqrt(dxHalf * dxHalf + dyHalf * dyHalf)

        // angle between start point and end point
        var angle = Math.toDegrees(atan2(-dy, -dx))
        angle = Math.toRadians(CONTROL_POINT_ANGLE_OFFSET - angle)

        // coordinates of control point from (0, 0)
        val x = r * cos(angle)
        val y = r * sin(angle)

        return Point(midPoint.x + x, midPoint.y - y) to Point(midPoint.x - x, midPoint.y + y)
    }

}
