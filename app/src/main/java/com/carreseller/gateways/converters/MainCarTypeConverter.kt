package com.carreseller.gateways.converters

import com.carreseller.domain.MainCarType
import com.carreseller.domain.MainCarTypeList
import com.carreseller.domain.Manufacturer
import com.carreseller.domain.ManufacturerList
import com.carreseller.gateways.remote.response.MainTypesResponse
import com.carreseller.gateways.remote.response.ManufacturerResponse

object MainCarTypeConverter {

    fun fromResponse(response: Map<String, String>): List<MainCarType> {
        return mutableListOf<MainCarType>().apply {
            response.keys.forEach {
                add(MainCarType(it))
            }
        }
    }

    fun fromResponse(response: MainTypesResponse): MainCarTypeList {
        return MainCarTypeList(
            fromResponse(response.wkda),
            response.totalPageCount
        )
    }
}
