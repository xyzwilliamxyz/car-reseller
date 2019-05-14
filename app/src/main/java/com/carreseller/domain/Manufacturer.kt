package com.carreseller.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Manufacturer(
    val manufacturerId: String,
    val name: String
) : Parcelable, SearchItem {

    override fun getValue() = name
}

data class ManufacturerList(
    val list: List<Manufacturer>,
    val totalPageCount: Int
)
