package com.example.flights.presentation.ui.pick_destination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flights.domain.interactor.PickDestinationInteractor
import com.example.flights.domain.model.Destination
import com.example.flights.presentation.base.BaseViewModel
import com.example.flights.presentation.schedulers.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PickDestinationViewModel @Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val interactor: PickDestinationInteractor,
    private val router: Router
) : BaseViewModel() {

    companion object {
        private const val DEBOUNCE_TIMEOUT_MILLIS = 300L
    }

    private val lastQuerySubject: PublishSubject<String> = PublishSubject.create<String>()

    private val stateLiveData = MutableLiveData<PickDestinationViewState>(PickDestinationViewState.Idle)

    init {
        lastQuerySubject
            .debounce(DEBOUNCE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { query ->
                if (query.isEmpty()) {
                    stateLiveData.postValue(PickDestinationViewState.Idle)
                    Observable.just(emptyList())
                } else {
                    stateLiveData.postValue(PickDestinationViewState.Loading)
                    Observable.just(query)
                        .observeOn(schedulersProvider.io())
                        .flatMapSingle(interactor::findDestinations)
                        .doOnError(errorEventInternal::postValue)
                        .onErrorReturnItem(emptyList())
                }
            }
            .observeOn(schedulersProvider.mainThread())
            .map(PickDestinationViewState::Content)
            .subscribe(stateLiveData::setValue, errorEventInternal::setValue)
            .untilDestroy()
    }

    fun getState(): LiveData<PickDestinationViewState> = stateLiveData

    fun onEditTextChanged(text: CharSequence?) {
        val query = text?.toString() ?: ""

        lastQuerySubject.onNext(query)
    }

    fun onDestinationClicked(destination: Destination, isDeparture: Boolean) {
        interactor.saveDestination(isDeparture, destination)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
            .subscribe(router::exit)
            .untilDestroy()
    }

    fun onUpButtonClicked() {
        router.exit()
    }

}
