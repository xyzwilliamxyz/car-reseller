package com.carreseller.gateways.remote.response

data class MainTypesResponse(
    val page: Int,
    val pageSize: Int,
    val totalPageCount: Int,
    val wkda: Map<String, String>
)
