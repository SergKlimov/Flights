package com.example.flights.data.network.api

import com.example.flights.data.network.response.CitiesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {

    @GET("autocomplete")
    fun getCities(
        @Query("term") query: String,
        @Query("lang") lang: String = "ru"
    ): Single<CitiesResponse>

}
