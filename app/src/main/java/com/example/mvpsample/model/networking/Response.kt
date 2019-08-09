package com.example.mvpsample.model.networking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class Response (
    val items: MutableList<Item>
)

@Parcelize
data class Item(
    val description: String?,
    val image: @RawValue Image? = null,
    val location: String?,
    val price: @RawValue Price?,
    val id: String,
    var isFavorite: Boolean = false): Parcelable

data class Image(
    val url: String? = null
)

data class Price(
    val value: Int? = null
)