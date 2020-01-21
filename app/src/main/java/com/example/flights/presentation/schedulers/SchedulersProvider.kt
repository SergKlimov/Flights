package com.example.flights.presentation.schedulers

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun io(): Scheduler

    fun computation(): Scheduler

    fun mainThread(): Scheduler

}
