package com.carreseller.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuiltDate(
    val date: String
) : Parcelable, SearchItem {

    override fun getValue() = date
}

data class BuiltDateList(
    val list: List<BuiltDate>
)
