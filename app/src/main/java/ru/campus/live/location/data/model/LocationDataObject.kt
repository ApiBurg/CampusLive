package ru.campus.live.location.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationDataObject(
    val id: Int,
    val name: String,
    val address: String,
    val type: Int
) : Parcelable
