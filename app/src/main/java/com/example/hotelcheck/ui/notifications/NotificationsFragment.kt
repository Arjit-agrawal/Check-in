package com.example.hotelcheck.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotelcheck.authentication.LogInActivity
import com.example.hotelcheck.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.FirebaseAuth

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val firebaseAuth = FirebaseAuth.getInstance()
        binding.signOut.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(requireActivity(), LogInActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
        return root
    }

}