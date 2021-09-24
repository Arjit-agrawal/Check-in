package com.example.hotelcheck.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.hotelcheck.MainActivity
import com.example.hotelcheck.databinding.ActivitySignUpBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val phoneNo = intent.getStringExtra("phoneNo").toString()
        val userId = intent.getStringExtra("userId").toString()

        binding.createAccount.setOnClickListener {
            if (validName(binding.firstName) || validName(binding.lastName) ||
                    validEmail(binding.email) || validId(binding.id)) return@setOnClickListener
            val firstName = binding.firstName.editText?.text.toString()
            val lastName = binding.lastName.editText?.text.toString()
            val email = binding.email.editText?.text.toString()
            val id = binding.id.editText?.text.toString()
            val hashMap = HashMap<String, Any>()
            hashMap["userId"] = userId
            hashMap["phoneNo"] = phoneNo
            hashMap["firstName"] = firstName
            hashMap["lastName"] = lastName
            hashMap["email"] = email
            hashMap["id"] = id
            FirebaseFirestore.getInstance().collection("Users").document(userId).set(hashMap)
                .addOnCompleteListener {
                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }

    private fun validName(textInputLayout: TextInputLayout) : Boolean {
        val string = textInputLayout.editText?.text.toString()
        return if (string.isEmpty() || string.length > 30) {
            textInputLayout.error = "Invalid Name"
            true
        }
        else false
    }
    private fun validEmail(textInputLayout: TextInputLayout) : Boolean {
        val string = textInputLayout.editText?.text.toString()
        return if (string.isEmpty() || string.length > 50 || string.isDigitsOnly()) {
            textInputLayout.error = "Invalid Email"
            true
        }
        else false
    }
    private fun validId(textInputLayout: TextInputLayout) : Boolean {
        val string = textInputLayout.editText?.text.toString()
        return if (string.isEmpty() || !string.isDigitsOnly() || string.length != 12) {
            textInputLayout.error = "Invalid Id"
            true
        }
        else false
    }
}