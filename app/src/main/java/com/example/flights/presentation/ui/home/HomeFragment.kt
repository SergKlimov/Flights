package com.example.flights.presentation.ui.home

import android.os.Bundle
import android.view.View
import com.example.flights.R
import com.example.flights.presentation.base.BaseFragment
import com.example.flights.presentation.exception.SameDestinationsException
import com.example.flights.presentation.extension.getViewModel
import com.example.flights.presentation.extension.observe
import kotlinx.android.synthetic.main.fragment_home.fragment_home_button_arrival
import kotlinx.android.synthetic.main.fragment_home.fragment_home_button_departure
import kotlinx.android.synthetic.main.fragment_home.fragment_home_button_search
import kotlinx.android.synthetic.main.fragment_home.fragment_home_text_view_arrival_city
import kotlinx.android.synthetic.main.fragment_home.fragment_home_text_view_arrival_iata
import kotlinx.android.synthetic.main.fragment_home.fragment_home_text_view_departure_city
import kotlinx.android.synthetic.main.fragment_home.fragment_home_text_view_departure_iata

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by lazy { getViewModel(HomeViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_home_button_departure.setOnClickListener {
            viewModel.onPickDestinationClicked(isDeparture = true)
        }

        fragment_home_button_arrival.setOnClickListener {
            viewModel.onPickDestinationClicked(isDeparture = false)
        }

        fragment_home_button_search.setOnClickListener {
            viewModel.onSearchClicked()
        }

        observe(viewModel.getErrorEvent(), this::onErrorEvent)

        observe(viewModel.getRouteLiveData()) { route ->
            with(route.departure) {
                fragment_home_text_view_departure_iata.text = iata
                fragment_home_text_view_departure_city.text = city
            }
            with(route.arrival) {
                fragment_home_text_view_arrival_iata.text = iata
                fragment_home_text_view_arrival_city.text = city
            }
        }
    }

    override fun onErrorEvent(throwable: Throwable) {
        if (throwable is SameDestinationsException) {
            showError(R.string.fragment_home_error_same_destinations)
        } else {
            super.onErrorEvent(throwable)
        }
    }

}
