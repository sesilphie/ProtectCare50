package com.ubaya.protectcare50

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckIn(val placeName:String, val checkInTime:String) : Parcelable{
    override fun toString(): String = placeName + checkInTime
}
