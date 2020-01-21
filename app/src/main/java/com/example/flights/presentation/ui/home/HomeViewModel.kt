package com.example.flights.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flights.domain.interactor.HomeInteractor
import com.example.flights.domain.model.Route
import com.example.flights.presentation.exception.SameDestinationsException
import com.example.flights.presentation.base.BaseViewModel
import com.example.flights.presentation.navigation.Screens
import com.example.flights.presentation.schedulers.SchedulersProvider
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val router: Router,
    schedulersProvider: SchedulersProvider,
    homeInteractor: HomeInteractor
) : BaseViewModel() {

    private val route = MutableLiveData<Route>()

    init {
        homeInteractor.observeRecentRoute()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
            .subscribe(route::setValue, errorEventInternal::setValue)
            .untilDestroy()
    }

    fun getRouteLiveData(): LiveData<Route> = route

    fun onPickDestinationClicked(isDeparture: Boolean) {
        router.navigateTo(Screens.PickDestination(isDeparture))
    }

    fun onSearchClicked() {
        val departure = route.value?.departure?.city
        val arrival = route.value?.arrival?.city

        if (departure == arrival) {
            errorEventInternal.setValue(SameDestinationsException())
        } else {

        }
    }

}
