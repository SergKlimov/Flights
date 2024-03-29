package com.example.flights.presentation.navigation

import androidx.fragment.app.Fragment
import com.example.flights.presentation.ui.home.HomeFragment
import com.example.flights.presentation.ui.pick_destination.PickDestinationFragment
import com.example.flights.presentation.ui.search_tickets.SearchTicketsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class Home : SupportAppScreen() {
        override fun getFragment(): Fragment = HomeFragment.newInstance()
    }

    data class PickDestination(val isDeparture: Boolean) : SupportAppScreen() {
        override fun getFragment(): Fragment = PickDestinationFragment.newInstance(isDeparture)
    }

    class SearchTickets : SupportAppScreen() {
        override fun getFragment(): Fragment = SearchTicketsFragment.newInstance()
    }

}
