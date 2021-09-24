package com.example.hotelcheck.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hotelcheck.dao.BookingHistoryDao
import com.example.hotelcheck.databinding.ActivityPaymentBinding
import com.example.hotelcheck.model.HistoryModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

class PaymentActivity : AppCompatActivity() {

    lateinit var binding : ActivityPaymentBinding
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = intent.getParcelableExtra<HistoryModel>("historyModel")

        /*val firstName = bundle?.get("firstName").toString()
        val lastName = bundle?.get("lastName").toString()
        val id = bundle?.get("id").toString()
        val phoneNo = bundle?.get("phoneNo").toString()
        val email = bundle?.get("email").toString()
        val checkIn = bundle?.get("checkIn").toString()
        val totalPrice = bundle?.get("totalPrice").toString()
        val security = bundle?.get("security").toString()
        val service = bundle?.get("service").toString()
        val totalRoomPrice = bundle?.get("totalRoomPrice").toString()
        val noOfDays = bundle?.get("noOfDays").toString()
        val hotelName = bundle?.get("hotelName").toString()
        val roomName = bundle?.get("roomName").toString()
        val roomPrice = bundle?.get("roomPrice").toString()
        val hotelImage = bundle?.getInt("hotelImage")!!.toInt()
        val roomImage = bundle.getInt("roomImage")*/

        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

        binding.confirmBooking.setOnClickListener {
            binding.confirmBooking.startAnimation()
            val calendar = Calendar.getInstance()
            val simpleDateFormat = SimpleDateFormat("dd / MM / yyyy")
            val simpleTimeFormat = SimpleDateFormat("HH : mm")
            val date = simpleDateFormat.format(calendar.time)
            val time = simpleTimeFormat.format(calendar.time)
            val random = Random
            val roomNumber = random.nextInt(100, 400)
            model!!.userId = userId
            model.roomNumber = roomNumber.toString()
            model.bookingDate = date
            model.bookingTime = time
            model.bookingState = "1"
            /*val hashMap = HashMap<String, Any>()
            hashMap["firstName"] = firstName
            hashMap["lastName"] = lastName
            hashMap["id"] = id
            hashMap["phoneNo"] = phoneNo
            hashMap["email"] = email
            hashMap["checkIn"] = checkIn
            hashMap["totalRoomPrice"] = totalRoomPrice
            hashMap["security"] = security
            hashMap["service"] = service
            hashMap["totalPrice"] = totalPrice
            hashMap["noOfDays"] = noOfDays
            hashMap["userId"] = userId
            hashMap["hotelName"] = hotelName
            hashMap["roomName"] = roomName
            hashMap["roomNumber"] = roomNumber
            hashMap["roomPrice"] = roomPrice
            hashMap["hotelImage"] = hotelImage
            hashMap["roomImage"] = roomImage
            hashMap["bookingState"] = "1"
            hashMap["bookingDate"] = date
            hashMap["bookingTime"] = time*/

            FirebaseFirestore.getInstance().collection("Booking").document()
                .set(model).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Booking Confirm", Toast.LENGTH_SHORT).show()
                    binding.confirmBooking.revertAnimation()
                    val intent = Intent(applicationContext, PaymentSuccessActivity::class.java)
                    intent.putExtra("roomNumber", roomNumber.toString())
                    startActivity(intent)
                }
        }
        binding.back.setOnClickListener {
            finish()
        }
    }
}