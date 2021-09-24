package com.example.hotelcheck.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hotelcheck.authentication.LogInActivity
import com.example.hotelcheck.databinding.ActivityHotelDescBinding
import com.example.hotelcheck.model.HotelModel


class HotelDesc : AppCompatActivity() {

    lateinit var binding : ActivityHotelDescBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelDescBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hotelModel : HotelModel? = intent.getParcelableExtra<HotelModel>("hotelObject")
        var hotelName = ""
        var hotelImage = 0
        hotelModel?.let {
            hotelImage = it.hotelImage
            binding.image.setImageResource(hotelImage)
            binding.hotelName.text = it.hotelName
        }

        binding.checkIn.setOnClickListener {
            val intent = Intent(applicationContext, HotelRoom::class.java)
            intent.putExtra("hotelName", hotelModel)
            startActivity(intent)
        }
        binding.back.setOnClickListener {
            finish()
        }
    }
}