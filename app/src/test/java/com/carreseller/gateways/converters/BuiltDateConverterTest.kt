package com.carreseller.gateways.converters

import com.carreseller.BaseTest
import com.carreseller.domain.BuiltDate
import com.carreseller.gateways.remote.response.BuiltDatesResponse
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.junit.Assert.assertThat
import org.junit.Test

class BuiltDateConverterTest : BaseTest() {

    @Test
    fun fromResponse_ConvertedListWith2Elements_HashMapWith2Entries() {
        val response = hashMapOf("2016" to "2016", "2017" to "2017")
        val result = BuiltDateConverter.fromResponse(response)

        assertThat(result.size, equalTo(2))
        assertThat(result, hasItems(BuiltDate("2016"), BuiltDate("2017")))
    }

    @Test
    fun fromResponse_EmptyConvertedList_EmptyHashMap() {
        val response = hashMapOf<String, String>()
        val result = BuiltDateConverter.fromResponse(response)

        assertThat(result.size, equalTo(0))
    }

    @Test
    fun fromResponse_BuiltDateListWith2Elements_BuiltDatesResponseWith2Entries() {
        val response = BuiltDatesResponse(hashMapOf("2016" to "2016", "2017" to "2017"))
        val result = BuiltDateConverter.fromResponse(response)

        assertThat(result.list.size, equalTo(2))
    }
}
