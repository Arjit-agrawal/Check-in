package com.example.hotelcheck.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class HistoryModel (
    val firstName : String = "",
    var userId : String = "",
    val lastName : String = "",
    val id : String = "",
    val email : String = "",
    val phoneNo : String = "",
    val checkIn : String = "",
    val totalPrice : String = "",
    val security : String = "",
    val service : String = "",
    val totalRoomPrice : String = "",
    val noOfDays : String = "",
    val roomImage : Int = 0,
    val roomName : String = "",
    val roomPrice : String = "",
    var roomNumber : String = "",
    val hotelImage : Int = 0,
    val hotelName : String = "",
    var bookingDate : String = "",
    var bookingTime : String = "",
    var bookingState : String = "",
) : Parcelable {

}
