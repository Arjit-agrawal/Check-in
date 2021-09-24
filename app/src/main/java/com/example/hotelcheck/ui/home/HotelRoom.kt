package com.example.hotelcheck.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelcheck.R
import com.example.hotelcheck.adapter.RoomAdapter
import com.example.hotelcheck.databinding.ActivityHotelRoomBinding
import com.example.hotelcheck.listener.RVRoomListener
import com.example.hotelcheck.model.HotelModel
import com.example.hotelcheck.model.RoomModel

class HotelRoom : AppCompatActivity(), RVRoomListener {

    lateinit var binding: ActivityHotelRoomBinding
    private var hotel : HotelModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hotel = intent.getParcelableExtra("hotelName")
        val list = ArrayList<RoomModel>()

        list.add(RoomModel(R.drawable.room11, "Double Bed Room", "899"))
        list.add(RoomModel(R.drawable.room2, "Single Bed Room", "999"))
        list.add(RoomModel(R.drawable.room3, "Mini Suite", "1099"))
        list.add(RoomModel(R.drawable.room4, "Triple Bed Room", "1199"))
        list.add(RoomModel(R.drawable.room5, "President Suite", "1299"))
        list.add(RoomModel(R.drawable.room6, "Murphy Room", "1359"))
        list.add(RoomModel(R.drawable.room7, "Cabana", "1599"))
        binding.roomRecyclerView.adapter = RoomAdapter(list, this)
        binding.roomRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.back.setOnClickListener {
            finish()
        }
    }
    override fun onItemClick(model: RoomModel) {
        val intent = Intent(applicationContext, BookingActivity::class.java)
        intent.putExtra("roomObject", model)
        intent.putExtra("hotelName", hotel)
        startActivity(intent)
    }
}