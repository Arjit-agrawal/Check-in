package com.example.hotelcheck.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import com.example.hotelcheck.InputValidation
import com.example.hotelcheck.databinding.ActivityLogInBinding
import com.google.android.material.textfield.TextInputLayout

class LogInActivity : AppCompatActivity() {

    lateinit var binding : ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextPhone.setOnClickListener {
            binding.phoneNo.error = null
            binding.phoneNo.isErrorEnabled = false
        }

        binding.getOtp.setOnClickListener {
            val phoneNumber = binding.phoneNo.editText?.text.toString()
            if (validPhone(phoneNumber)) return@setOnClickListener
            val intent = Intent(applicationContext, CodeView::class.java)
            intent.putExtra("phoneNo", phoneNumber)
            startActivity(intent)
        }
    }
    private fun validPhone(string : String) : Boolean {
        return if (string.isEmpty() || string.length != 10 || !string.isDigitsOnly()) {
            binding.phoneNo.error = "Invalid Phone"
            true
        } else false
    }
}