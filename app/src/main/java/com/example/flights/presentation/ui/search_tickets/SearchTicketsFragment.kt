package com.example.flights.presentation.ui.search_tickets

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.example.flights.R
import com.example.flights.presentation.base.BaseFragment
import com.example.flights.presentation.extension.getColor
import com.example.flights.presentation.extension.getViewModel
import com.example.flights.presentation.extension.observe
import com.example.flights.presentation.utils.map.CubicCurveEvaluator
import com.example.flights.presentation.utils.map.GoogleProjection
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.SphericalUtil
import com.google.maps.android.ui.IconGenerator
import java.util.concurrent.TimeUnit

class SearchTicketsFragment : BaseFragment(R.layout.fragment_map), OnMapReadyCallback {

    companion object {
        private const val CURRENT_FRACTION_KEY = "CURRENT_FRACTION_KEY"
        private const val PLANE_ICON_ANGLE_OFFSET = 90
        private const val PATH_POLYLINE_GAP_PX = 20f
        private const val PATH_POLYLINE_WIDTH_PX = 15f
        private const val MAX_ANIMATION_FRACTION = 1f
        private val ANIMATION_DURATION = TimeUnit.SECONDS.toMillis(10)

        fun newInstance() = SearchTicketsFragment()
    }

    private val viewModel by lazy { getViewModel(SearchTicketsViewModel::class.java) }

    private val projection = GoogleProjection()

    private lateinit var cubicCurveEvaluator: CubicCurveEvaluator

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var planeMarker: Marker

    private var currentFraction: Float = 0f
    private var fromSavedState: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view as MapView
        mapView.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            currentFraction = savedInstanceState.getFloat(CURRENT_FRACTION_KEY)
            fromSavedState = true
        }

        observe(viewModel.getErrorEvent(), this::onErrorEvent)

        mapView.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
        outState.putFloat(CURRENT_FRACTION_KEY, currentFraction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        observe(viewModel.getMapDataLiveData()) { data ->
            cubicCurveEvaluator = CubicCurveEvaluator(data.controlPoints)

            initializeCamera(data)
            initializeMarkers(data)
            initializePath(data.pathLatLng)
            animate()
        }

        viewModel.onMapReady(projection)
    }

    private fun initializeCamera(mapData: MapData) {
        if (!fromSavedState) {

            val departureLatLng = mapData.pathLatLng.first()
            val arrivalLatLng = mapData.pathLatLng.last()

            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val padding = (width * 0.15).toInt() // offset from edges of the map - 15% of screen

            val bounds = LatLngBounds.builder()
                .include(departureLatLng)
                .include(arrivalLatLng)
                .build()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding))
        }
    }

    private fun initializePath(path: List<LatLng>) {
        val pathBezier = PolylineOptions()
            .pattern(listOf(Dot(), Gap(PATH_POLYLINE_GAP_PX)))
            .color(getColor(R.color.colorMapPath))
            .width(PATH_POLYLINE_WIDTH_PX)
            .addAll(path)

        googleMap.addPolyline(pathBezier)
    }

    private fun initializeMarkers(mapData: MapData) {
        val planeMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_plane)

        val iconGenerator = IconGenerator(activity).apply {
            val textView = TextView(activity, null, 0, R.style.PinText).apply {
                id = com.google.maps.android.R.id.amu_text
            }
            setContentView(textView)
            setBackground(activity?.getDrawable(R.drawable.fragment_search_tickets_pin_background))
        }

        val startLatLng = mapData.pathLatLng.first()
        val endLatLng = mapData.pathLatLng.last()

        googleMap.setOnMarkerClickListener { true }
        googleMap.addMarker(createDestinationMarker(mapData.departure.iata, startLatLng, iconGenerator))
        googleMap.addMarker(createDestinationMarker(mapData.arrival.iata, endLatLng, iconGenerator))

        val position = projection.toLatLng(cubicCurveEvaluator.evaluate(currentFraction))
        val heading = SphericalUtil.computeHeading(position, endLatLng).toFloat() - PLANE_ICON_ANGLE_OFFSET

        planeMarker = googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .icon(planeMarkerIcon)
                .zIndex(1f)
                .flat(true)
                .rotation(heading)
                .anchor(0.5f, 0.5f)
        )
    }

    private fun createDestinationMarker(iata: String, latLng: LatLng, iconGenerator: IconGenerator): MarkerOptions {
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(iata))

        return MarkerOptions()
            .anchor(0.5f, 0.5f)
            .position(latLng)
            .icon(bitmapDescriptor)
    }

    private fun animate() {
        val duration = ((MAX_ANIMATION_FRACTION - currentFraction) * ANIMATION_DURATION).toLong()
        val animator: ValueAnimator = ValueAnimator.ofFloat(currentFraction, MAX_ANIMATION_FRACTION)
            .setDuration(duration)

        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            currentFraction = it.animatedValue as Float

            val nextPoint = cubicCurveEvaluator.evaluate(currentFraction)
            val nextPosition = projection.toLatLng(nextPoint)

            val bearings = SphericalUtil.computeHeading(planeMarker.position, nextPosition) - PLANE_ICON_ANGLE_OFFSET

            planeMarker.rotation = bearings.toFloat()
            planeMarker.position = nextPosition
        }

        if (duration != 0L) {
            animator.start()
        }
    }

}
