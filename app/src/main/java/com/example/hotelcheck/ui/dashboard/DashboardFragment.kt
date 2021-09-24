package com.example.hotelcheck.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelcheck.adapter.BookingHotelAdapter
import com.example.hotelcheck.databinding.FragmentDashboardBinding
import com.example.hotelcheck.listener.RVBookingListener
import com.example.hotelcheck.model.BookingHotelModel
import com.example.hotelcheck.model.HistoryModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class DashboardFragment : Fragment(), RVBookingListener {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val query : Query = FirebaseFirestore.getInstance().collection("Booking")
            .whereEqualTo("userId", userId).orderBy("checkIn", Query.Direction.DESCENDING)

        val option = FirestoreRecyclerOptions.Builder<HistoryModel>().setQuery(query, HistoryModel::class.java).build()
        val adapter = BookingHotelAdapter(option, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter.startListening()

        return root
    }

    override fun onItemClickListener(itemId: String) {
        val intent = Intent(requireActivity(), BookingDetailActivity::class.java)
        intent.putExtra("key", itemId)
        startActivity(intent)
    }


}