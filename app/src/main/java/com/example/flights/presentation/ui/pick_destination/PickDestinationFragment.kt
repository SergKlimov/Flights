package com.example.flights.presentation.ui.pick_destination

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.flights.R
import com.example.flights.domain.model.Destination
import com.example.flights.presentation.base.BaseFragment
import com.example.flights.presentation.extension.getViewModel
import com.example.flights.presentation.extension.observe
import com.example.flights.presentation.extension.showSoftInput
import com.example.flights.presentation.extension.withArgs
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.fragment_pick_destination.fragment_pick_destination_edit_text
import kotlinx.android.synthetic.main.fragment_pick_destination.fragment_pick_destination_progress
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

    private val viewModel by lazy { getViewModel(PickDestinationViewModel::class.java) }

    private var isDeparture = false

    private val delegate = adapterDelegateLayoutContainer<Destination, Destination>(R.layout.item_destination) {

        itemView.setOnClickListener { viewModel.onDestinationClicked(item, isDeparture) }

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
            viewModel.onEditTextChanged(text)
        }
        fragment_pick_destination_recycler_view.adapter = adapter

        observeViewModel()
    }

    override fun onUpButtonClicked() {
        viewModel.onUpButtonClicked()
    }

    private fun observeViewModel() {
        observe(viewModel.getState()) { viewState ->
            when (viewState) {
                is PickDestinationViewState.Loading -> {
                    fragment_pick_destination_progress.isVisible = true
                }
                is PickDestinationViewState.Content -> {
                    fragment_pick_destination_progress.isInvisible = true
                    adapter.items = viewState.destinations
                    adapter.notifyDataSetChanged()
                }
            }
        }
        observe(viewModel.getErrorEvent(), this::onErrorEvent)
    }

}
