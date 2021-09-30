package com.example.hotelcheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.hotelcheck.authentication.LogInActivity
import com.example.hotelcheck.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firebaseAuth = FirebaseAuth.getInstance()
        Handler(Looper.getMainLooper()).postDelayed({
            if (firebaseAuth.currentUser != null) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(applicationContext, LogInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1000)
    }
}