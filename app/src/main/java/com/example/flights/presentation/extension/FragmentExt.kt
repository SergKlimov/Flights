package com.example.flights.presentation.extension

import android.os.Bundle
import androidx.fragment.app.Fragment

fun <T : Fragment> T.withArgs(receiver: Bundle.() -> Unit): T {
    arguments = Bundle().apply(receiver)
    return this
}
