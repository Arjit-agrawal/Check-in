package com.example.hotelcheck.listener

import com.example.hotelcheck.model.HotelModel

interface RVHotelListener {
    fun onItemClicked(model: HotelModel)
}