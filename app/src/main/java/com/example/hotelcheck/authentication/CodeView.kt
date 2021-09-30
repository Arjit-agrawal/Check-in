package com.example.hotelcheck.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelcheck.MainActivity
import com.example.hotelcheck.databinding.ActivityCodeViewBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.TimeUnit

class CodeView : AppCompatActivity() {

    var codeSentBySystem: String? = null
    private var phoneNo : String = "+91"
    lateinit var binding : ActivityCodeViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        phoneNo += intent.getStringExtra("phoneNo").toString()

        sendVerificationCodeToUser(phoneNo)
        binding.verifyCode.setOnClickListener {
            val code = Objects.requireNonNull(binding.pinView.text).toString()
            if (code.isNotEmpty()) verifyCode(code)
        }
    }

    private fun sendVerificationCodeToUser(_phoneNo: String?) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(_phoneNo!!) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                codeSentBySystem = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                Toast.makeText(applicationContext, code, Toast.LENGTH_SHORT).show()
                if (code != null) with(binding) { pinView.setText(code) }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@CodeView, e.message, Toast.LENGTH_LONG).show()
            }
        }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verification Completed", Toast.LENGTH_LONG).show()
                    FirebaseFirestore.getInstance().collection("Users")
                        .whereEqualTo("userId", firebaseAuth.currentUser?.uid.toString())
                        .get().addOnCompleteListener {
                            if (it.isSuccessful) {
                                if (it.result!!.size() == 0) {
                                    val intent = Intent(applicationContext, SignUpActivity::class.java)
                                    intent.putExtra("phoneNo", phoneNo)
                                    intent.putExtra("userId", firebaseAuth.currentUser?.uid.toString())
                                    startActivity(intent)
                                    finish()
                                } else {
                                    val intent = Intent(baseContext, MainActivity::class.java)
                                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    startActivity(intent)
                                    finishAffinity()
                                }
                            }
                        }
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException)
                        Toast.makeText(this, "Verification is Not Completed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun verifyCode(code: String) {
        val phoneAuthCredential = PhoneAuthProvider.getCredential(codeSentBySystem!!, code)
        signInWithPhoneAuthCredential(phoneAuthCredential)
    }
}