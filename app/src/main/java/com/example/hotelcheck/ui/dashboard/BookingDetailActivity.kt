package com.example.hotelcheck.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelcheck.databinding.ActivityBookingDetailBinding
import com.example.hotelcheck.model.HistoryModel
import com.google.firebase.firestore.FirebaseFirestore

class BookingDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityBookingDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val documentId : String = intent.getStringExtra("key").toString()

        FirebaseFirestore.getInstance().collection("Booking").document(documentId)
            .get().addOnSuccessListener {
                if (it.exists()) {
                    val model : HistoryModel? = it.toObject(HistoryModel::class.java)
                    binding.roomImage.setImageResource(model!!.hotelImage)
                    binding.roomName.text = model.roomName
                    binding.roomPrice.text = model.roomPrice.plus(" /-")
                    binding.totalPrice.text = model.totalPrice
                    binding.totalRoomPrice.text = model.totalRoomPrice
                    binding.security.text = model.security
                    binding.service.text = model.service
                    binding.noOfDays.text = model.noOfDays
                    binding.name.text = model.firstName.plus( " ").plus(model.lastName)
                    binding.email.text = model.email
                    binding.phoneNo.text = model.phoneNo
                    binding.id.text = model.id
                    binding.roomNo.text = model.roomNumber
                    binding.stayInfo.text = model.checkIn
                }
            }
        binding.back.setOnClickListener {
            finish()
        }
    }
}