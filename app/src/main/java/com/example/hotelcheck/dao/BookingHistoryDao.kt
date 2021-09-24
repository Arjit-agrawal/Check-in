package com.example.hotelcheck.dao

import android.content.Intent
import android.widget.Toast
import com.example.hotelcheck.model.HistoryModel
import com.example.hotelcheck.ui.home.PaymentSuccessActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class BookingHistoryDao {

    private val bookingCollection = FirebaseFirestore.getInstance().collection("Booking")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun createBooking(historyModel: HistoryModel?) {
        val task = null
        historyModel?.let { runBlocking(Dispatchers.IO) { bookingCollection.document().set(it)
            .addOnSuccessListener {

            }
        } }
    }

}