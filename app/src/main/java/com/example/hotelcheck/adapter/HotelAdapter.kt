package com.example.hotelcheck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelcheck.R
import com.example.hotelcheck.listener.RVHotelListener
import com.example.hotelcheck.model.HotelModel

class HotelAdapter(private val list : ArrayList<HotelModel>,
                   private val listener : RVHotelListener) : RecyclerView.Adapter<HotelAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hotelImage : ImageView = itemView.findViewById(R.id.hotelImage)
        val hotelName : TextView = itemView.findViewById(R.id.hotelName)
        val hotelDesc : TextView = itemView.findViewById(R.id.hotelDescription)
        val bookNow : Button = itemView.findViewById(R.id.bookNowHotel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sample_hotel, parent, false))
        view.bookNow.setOnClickListener {
            listener.onItemClicked(list[view.adapterPosition])
        }
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.hotelImage.setImageResource(model.hotelImage)
        holder.hotelName.text = model.hotelName
        holder.hotelDesc.text = model.hotelDesc
    }

    override fun getItemCount(): Int = list.size
}