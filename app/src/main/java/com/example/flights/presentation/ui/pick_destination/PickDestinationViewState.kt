package com.example.flights.presentation.ui.pick_destination

import com.example.flights.domain.model.Destination

sealed class PickDestinationViewState {

    object Idle : PickDestinationViewState()
    object Loading : PickDestinationViewState()
    data class Content(val destinations: List<Destination>) : PickDestinationViewState()

}
