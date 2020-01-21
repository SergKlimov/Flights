package com.example.flights.presentation.extension

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun <T : Fragment> T.withArgs(receiver: Bundle.() -> Unit): T {
    arguments = Bundle().apply(receiver)
    return this
}

fun Fragment.getColor(colorId: Int) = ContextCompat.getColor(this.requireContext(), colorId)
