package com.example.hotelcheck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelcheck.R
import com.example.hotelcheck.listener.RVRoomListener
import com.example.hotelcheck.model.RoomModel

class RoomAdapter(private val list: ArrayList<RoomModel>, private val listener : RVRoomListener) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roomImage : ImageView = itemView.findViewById(R.id.roomImage)
        val roomName : TextView = itemView.findViewById(R.id.roomName)
        val roomPrice : TextView = itemView.findViewById(R.id.roomPrice)
        val selectRoom : Button = itemView.findViewById(R.id.selectRoom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sample_room, parent, false))
        view.selectRoom.setOnClickListener {
            listener.onItemClick(list[view.adapterPosition])
        }
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.roomImage.setImageResource(model.roomImage)
        holder.roomName.text = model.roomName
        holder.roomPrice.text = model.roomPrice.plus(" /-")
    }

    override fun getItemCount(): Int = list.size
}