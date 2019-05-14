package com.carreseller.gateways.remote

import com.carreseller.gateways.remote.response.BuiltDatesResponse
import com.carreseller.gateways.remote.response.MainTypesResponse
import com.carreseller.gateways.remote.response.ManufacturerResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CarTypesApi {

    @GET("car-types/manufacturer")
    fun getManufacturers(@Query("page") page: Int,
                         @Query("pageSize") pageSize: Int): Observable<ManufacturerResponse>

    @GET("car-types/main-types")
    fun getMainTypes(@Query("page") page: Int,
                     @Query("pageSize") pageSize: Int,
                     @Query("manufacturer") manufacturerId: String): Observable<MainTypesResponse>

    @GET("car-types/built-dates")
    fun getBuiltDates(@Query("manufacturer") manufacturerId: String,
                      @Query("main-type") mainType: String): Observable<BuiltDatesResponse>
}
