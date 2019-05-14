package com.carreseller.gateways.converters

import com.carreseller.domain.BuiltDate
import com.carreseller.domain.BuiltDateList
import com.carreseller.gateways.remote.response.BuiltDatesResponse

object BuiltDateConverter {

    fun fromResponse(response: Map<String, String>): List<BuiltDate> {
        return mutableListOf<BuiltDate>().apply {
            response.keys.forEach {
                add(BuiltDate(it))
            }
        }
    }

    fun fromResponse(response: BuiltDatesResponse): BuiltDateList {
        return BuiltDateList(
            fromResponse(response.wkda)
        )
    }
}
