package com.carreseller.gateways.converters

import com.carreseller.BaseTest
import com.carreseller.domain.Manufacturer
import com.carreseller.gateways.remote.response.MainTypesResponse
import com.carreseller.gateways.remote.response.ManufacturerResponse
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.junit.Assert.assertThat
import org.junit.Test

class ManufacturerConverterTest : BaseTest() {

    @Test
    fun fromResponse_ConvertedListWith2Elements_HashMapWith2Entries() {
        val response = hashMapOf("107" to "Bentley", "130" to "BMW")
        val result = ManufacturerConverter.fromResponse(response)

        assertThat(result.size, equalTo(2))
        assertThat(result, hasItems(Manufacturer("107", "Bentley"), Manufacturer("130", "BMW")))
    }

    @Test
    fun fromResponse_EmptyConvertedList_EmptyHashMap() {
        val response = hashMapOf<String, String>()
        val result = ManufacturerConverter.fromResponse(response)

        assertThat(result.size, equalTo(0))
    }

    @Test
    fun fromResponse_ManufacturerListWith2ElementsAndExpectedTotalPageCount_MainTypesResponseWith2Entries() {
        val response = ManufacturerResponse(0, PAGE_SIZE, PAGE_COUNT, hashMapOf("107" to "Bentley", "130" to "BMW"))
        val result = ManufacturerConverter.fromResponse(response)

        assertThat(result.list.size, equalTo(2))
        assertThat(result.totalPageCount, equalTo(PAGE_COUNT))
    }
}
