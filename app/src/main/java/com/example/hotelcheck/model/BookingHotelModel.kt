package com.example.hotelcheck.model

data class BookingHotelModel(
    val hotelImage : Int = 0,
    val hotelName : String = "",
    val roomName : String = "",
    val checkIn : String = "",
    val bookingTime : String = "",
    val bookingState : String = "",
    val roomNumber : String = "",
    val totalPrice : String = ""
)