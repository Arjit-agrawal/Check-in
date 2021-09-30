package com.example.hotelcheck.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelcheck.R
import com.example.hotelcheck.listener.RVBookingListener
import com.example.hotelcheck.model.BookingHotelModel
import com.example.hotelcheck.model.HistoryModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class BookingHotelAdapter(options: FirestoreRecyclerOptions<HistoryModel>, private val listener : RVBookingListener) :
    FirestoreRecyclerAdapter<HistoryModel, BookingHotelAdapter.ViewHolder>(options) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hotelName : TextView = itemView.findViewById(R.id.bookingHotelName)
        val hotelImage : ImageView = itemView.findViewById(R.id.bookingHotelImage)
        val roomName : TextView = itemView.findViewById(R.id.bookingRoomName)
        val bookingDate : TextView = itemView.findViewById(R.id.bookingDate)
        val totalPrice : TextView = itemView.findViewById(R.id.price)
        val roomNumber : TextView = itemView.findViewById(R.id.bookingRoomNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.sample_booking_hotel, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: HistoryModel) {
        holder.hotelImage.setImageResource(model.hotelImage)
        holder.hotelName.text = model.hotelName
        holder.roomName.text = model.roomName
        holder.bookingDate.text = "Check-in - ".plus(model.checkIn)
        holder.totalPrice.text = model.totalPrice
        holder.roomNumber.text = "Room No - ".plus(model.roomNumber)
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(snapshots.getSnapshot(position).id)
        }
    }

}