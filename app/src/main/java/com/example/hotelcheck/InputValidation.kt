package com.example.hotelcheck

import android.text.Editable
import android.text.TextWatcher
import androidx.core.text.isDigitsOnly
import com.google.android.material.textfield.TextInputLayout
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

abstract class InputValidation(private val textInputLayout : TextInputLayout) : TextWatcher {

    abstract fun validate(textInputLayout: TextInputLayout, text : String)

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
    override fun afterTextChanged(p0: Editable?) {
        val text = textInputLayout.editText?.text.toString().trim()
        validate(textInputLayout, text)
    }
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        val text = textInputLayout.editText?.text.toString().trim()
        validate(textInputLayout, text)
    }

    fun validName(string : String) : Boolean {
        return if (string.isEmpty()) false
        else string.length <= 25
    }
    fun validCheckInOut(textInputLayout: TextInputLayout) : Boolean {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd / MM / yyyy", Locale.US)
        val date = simpleDateFormat.format(calendar.time).toString()
        val input = textInputLayout.editText?.text.toString()
        return input.isEmpty() || calculateDate(date, input) < 1 || calculateDate(date, input) > 3
    }
    fun validEmail(string : String) : Boolean {
        return (string.isEmpty() || string.length > 50 || string.isDigitsOnly())
    }
    fun validPhoneNo(string : String) : Boolean {
        return !(string.isEmpty() || string.length != 13)
    }
    fun validId(string : String) : Boolean {
        return !(string.isEmpty() || !string.isDigitsOnly() || string.length != 12)
    }
    private fun calculateDate(inputString1 : String, inputString2 : String) : Int {
        val myFormat = "dd / MM / yyyy"
        val simpleDateFormat = SimpleDateFormat(myFormat, Locale.US)
        var days = 0
        try {
            val date1 = simpleDateFormat.parse(inputString1)
            val date2 = simpleDateFormat.parse(inputString2)
            val diff = date2!!.time - date1!!.time
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return days
    }
}