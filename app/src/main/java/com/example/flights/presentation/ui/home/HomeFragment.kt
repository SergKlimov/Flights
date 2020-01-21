package com.example.flights.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.flights.R
import kotlinx.android.synthetic.main.fragment_home.fragment_home_button_arrival
import kotlinx.android.synthetic.main.fragment_home.fragment_home_button_departure
import kotlinx.android.synthetic.main.fragment_home.fragment_home_button_search


class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_home_button_departure.setOnClickListener {

        }

        fragment_home_button_arrival.setOnClickListener {

        }

        fragment_home_button_search.setOnClickListener {

        }
    }

}
