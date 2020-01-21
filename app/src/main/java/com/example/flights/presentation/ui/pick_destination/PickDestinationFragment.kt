package com.example.flights.presentation.ui.pick_destination

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.example.flights.R
import com.example.flights.domain.model.Destination
import com.example.flights.presentation.base.BaseFragment
import com.example.flights.presentation.extension.showSoftInput
import com.example.flights.presentation.extension.withArgs
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.fragment_pick_destination.fragment_pick_destination_edit_text
import kotlinx.android.synthetic.main.fragment_pick_destination.fragment_pick_destination_recycler_view
import kotlinx.android.synthetic.main.fragment_pick_destination.fragment_pick_destination_toolbar
import kotlinx.android.synthetic.main.item_destination.view.item_destination_full_name
import kotlinx.android.synthetic.main.item_destination.view.item_destination_iata

class PickDestinationFragment : BaseFragment(R.layout.fragment_pick_destination) {

    companion object {
        private const val DEPARTURE_ARG = "DEPARTURE_ARG"

        fun newInstance(isDeparture: Boolean) = PickDestinationFragment().withArgs {
            putBoolean(DEPARTURE_ARG, isDeparture)
        }
    }

    private var isDeparture = false

    private val delegate = adapterDelegateLayoutContainer<Destination, Destination>(R.layout.item_destination) {

        bind {
            itemView.item_destination_full_name.text = item.fullName
            itemView.item_destination_iata.text = item.iata
        }

    }

    private val adapter = ListDelegationAdapter(delegate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isDeparture = requireArguments().getBoolean(DEPARTURE_ARG)

        setToolbar(fragment_pick_destination_toolbar)

        fragment_pick_destination_edit_text.showSoftInput()
        fragment_pick_destination_edit_text.doOnTextChanged { text, _, _, _ ->

        }
        fragment_pick_destination_recycler_view.adapter = adapter

    }

    override fun onUpButtonClicked() {

    }

}
