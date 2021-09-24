package com.example.hotelcheck.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelcheck.R
import com.example.hotelcheck.adapter.HotelAdapter
import com.example.hotelcheck.databinding.FragmentHomeBinding
import com.example.hotelcheck.listener.RVHotelListener
import com.example.hotelcheck.model.HotelModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class HomeFragment : Fragment(), RVHotelListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var uid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val list = ArrayList<HotelModel>()

        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

        list.add(HotelModel(R.drawable.hotel1, "The Fresco Hotel", "A hotel is an establishment that provides paid lodging on a short-term basis."))
        list.add(HotelModel(R.drawable.hotel2, "Lake Place Inn", "A hotel is an establishment that provides paid lodging on a short-term basis."))
        list.add(HotelModel(R.drawable.hotel3, "Golden Cherry Motel", "A hotel is an establishment that provides paid lodging on a short-term basis."))
        list.add(HotelModel(R.drawable.hotel4, "Ananda", "A hotel is an establishment that provides paid lodging on a short-term basis."))
        list.add(HotelModel(R.drawable.hotel5,"Sunset Lodge", "A hotel is an establishment that provides paid lodging on a short-term basis."))
        list.add(HotelModel(R.drawable.hotel6,"Hotel Bliss", "A hotel is an establishment that provides paid lodging on a short-term basis."))
        list.add(HotelModel(R.drawable.hotel7,"The Glory Hotel", "A hotel is an establishment that provides paid lodging on a short-term basis."))
        binding.hotelRecyclerView.adapter = HotelAdapter(list, this)
        binding.hotelRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        runBlocking {
            getName()
        }
        return root
    }

    override fun onItemClicked(model: HotelModel) {
        val intent = Intent(requireActivity(), HotelDesc::class.java)
        intent.putExtra("hotelObject", model)
        startActivity(intent)
    }

    private suspend fun getName() = coroutineScope {
        launch {
            FirebaseFirestore.getInstance().collection("Users").document(uid).get()
                .addOnSuccessListener {
                    if (it.exists()) binding.userName.text = it.getString("firstName").toString()
                }
        }
    }
}