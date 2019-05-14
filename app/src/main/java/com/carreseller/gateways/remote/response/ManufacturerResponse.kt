package com.carreseller.gateways.remote.response

data class ManufacturerResponse(
    val page: Int,
    val pageSize: Int,
    val totalPageCount: Int,
    val wkda: Map<String, String>
)
