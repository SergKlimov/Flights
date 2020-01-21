package com.example.flights.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.flights.presentation.utils.live_data.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val errorEventInternal = SingleLiveEvent<Throwable>()

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun Disposable.untilDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    fun getErrorEvent(): LiveData<Throwable> = errorEventInternal

}
