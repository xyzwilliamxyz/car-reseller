package com.carreseller.gateways.converters

import com.carreseller.domain.Manufacturer
import com.carreseller.domain.ManufacturerList
import com.carreseller.gateways.remote.response.ManufacturerResponse

object ManufacturerConverter {

    fun fromResponse(response: Map<String, String>): List<Manufacturer> {
        return mutableListOf<Manufacturer>().apply {
            response.entries.forEach {
                add(Manufacturer(it.key, it.value))
            }
        }
    }

    fun fromResponse(response: ManufacturerResponse): ManufacturerList {
        return ManufacturerList(
            fromResponse(response.wkda),
            response.totalPageCount
        )
    }
}
