package com.example.hotelcheck.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class RoomModel(val roomImage : Int, val roomName : String, val roomPrice : String) : Parcelable