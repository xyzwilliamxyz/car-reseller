package com.carreseller.gateways.converters

import com.carreseller.BaseTest
import com.carreseller.domain.MainCarType
import com.carreseller.gateways.remote.response.MainTypesResponse
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.junit.Assert.assertThat
import org.junit.Test

class MainCarTypeConverterTest : BaseTest() {

    @Test
    fun fromResponse_ConvertedListWith2Elements_HashMapWith2Entries() {
        val response = hashMapOf("Z1" to "Z1", "M4" to "M4")
        val result = MainCarTypeConverter.fromResponse(response)

        assertThat(result.size, equalTo(2))
        assertThat(result, hasItems(MainCarType("Z1"), MainCarType("M4")))
    }

    @Test
    fun fromResponse_EmptyConvertedList_EmptyHashMap() {
        val response = hashMapOf<String, String>()
        val result = MainCarTypeConverter.fromResponse(response)

        assertThat(result.size, equalTo(0))
    }

    @Test
    fun fromResponse_MainCarTypeListWith2ElementsAndExpectedTotalPageCount_MainTypesResponseWith2Entries() {
        val response = MainTypesResponse(0, PAGE_SIZE, PAGE_COUNT, hashMapOf("Z1" to "Z1", "M4" to "M4"))
        val result = MainCarTypeConverter.fromResponse(response)

        assertThat(result.list.size, equalTo(2))
        assertThat(result.totalPageCount, equalTo(PAGE_COUNT))
    }
}
