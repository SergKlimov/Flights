package com.example.flights.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flights.FlightsApp
import com.example.flights.R
import com.example.flights.presentation.navigation.Screens
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navigator: Navigator by lazy(LazyThreadSafetyMode.NONE) {
        SupportAppNavigator(this, R.id.activity_main_fragments_container)
    }

    @Inject
    internal lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as FlightsApp).getAppComponent().inject(this)

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf(Replace(Screens.Home())))
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

}
