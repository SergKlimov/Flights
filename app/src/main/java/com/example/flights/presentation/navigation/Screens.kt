package com.example.flights.presentation.navigation

import androidx.fragment.app.Fragment
import com.example.flights.presentation.ui.home.HomeFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class Home : SupportAppScreen() {
        override fun getFragment(): Fragment = HomeFragment.newInstance()
    }

}
