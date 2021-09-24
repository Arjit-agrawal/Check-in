package com.example.hotelcheck.adapter

import androidx.core.text.isDigitsOnly
import com.google.android.material.textfield.TextInputLayout

class StringValidation {

    fun validCheckInOut(textInputLayout: TextInputLayout) : Boolean {
        if (textInputLayout.editText?.text.toString().isEmpty()) {
            textInputLayout.error = "Field cannot be empty"
            return true
        }
        else return false
    }
    fun validEmail(string : String) : Boolean {
        return (string.isEmpty() || string.length > 50 || string.isDigitsOnly())
    }
    fun validPhoneNo(string : String) : Boolean {
        return !(string.isEmpty() || string.length != 13)
    }
    fun validPhone(string : String) : Boolean {
        return !(string.isEmpty() || string.length != 13)
    }
    fun validId(string : String) : Boolean {
        return !(string.isEmpty() || !string.isDigitsOnly() || string.length != 12)
    }
}