package com.example.hotelcheck.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class HotelModel(val hotelImage : Int, val hotelName : String, val hotelDesc : String) : Parcelable