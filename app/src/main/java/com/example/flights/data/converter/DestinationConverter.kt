package com.example.flights.data.converter

import com.example.flights.domain.model.Destination
import com.f2prateek.rx.preferences2.Preference
import com.squareup.moshi.JsonAdapter
import java.io.IOException

class DestinationConverter(
    private val destinationJsonAdapter: JsonAdapter<Destination>
) : Preference.Converter<Destination> {

    override fun deserialize(serialized: String): Destination = destinationJsonAdapter.fromJson(serialized)
        ?: throw IOException("can't deserialize json: $serialized")

    override fun serialize(value: Destination): String = destinationJsonAdapter.toJson(value)

}
