package com.carreseller.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainCarType(
    val name: String
) : Parcelable, SearchItem {

    override fun getValue() = name
}

data class MainCarTypeList(
    val list: List<MainCarType>,
    val totalPageCount: Int
)
