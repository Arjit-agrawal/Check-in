package com.example.hotelcheck.ui.home

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelcheck.InputValidation
import com.example.hotelcheck.databinding.ActivityBookingBinding
import com.example.hotelcheck.model.HistoryModel
import com.example.hotelcheck.model.HotelModel
import com.example.hotelcheck.model.RoomModel
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class BookingActivity : AppCompatActivity() {

    private lateinit var uid : String
    private var totalDays : Int = 0
    private var totalRoomPrice : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roomModel : RoomModel? = intent.getParcelableExtra("roomObject")
        val hotel : HotelModel? = intent.getParcelableExtra("hotelName")
        roomModel?.roomImage?.let { binding.roomImage.setImageResource(it) }
        binding.roomName.text = roomModel?.roomName
        binding.roomPrice.text = roomModel?.roomPrice.plus(" /-")

        uid = FirebaseAuth.getInstance().uid.toString()
        FirebaseFirestore.getInstance().collection("Users").document(uid).get()
            .addOnSuccessListener {
            if (it.exists()) {
                binding.firstName.editText?.setText(it.getString("firstName"))
                binding.lastName.editText?.setText(it.getString("lastName"))
                binding.email.editText?.setText(it.getString("email"))
                binding.phoneNo.editText?.setText(it.getString("phoneNo"))
                binding.id.editText?.setText(it.getString("id"))
            }
        }

        binding.firstName.editText?.addTextChangedListener(object : InputValidation(binding.firstName) {
            override fun validate(textInputLayout: TextInputLayout, text: String) {
                if (!validName(text)) textInputLayout.error = "Invalid Name"
                else {
                    textInputLayout.error = null
                    textInputLayout.isErrorEnabled = false
                }
            }
        })
        binding.lastName.editText?.addTextChangedListener(object : InputValidation(binding.lastName) {
            override fun validate(textInputLayout: TextInputLayout, text: String) {
                if (!validName(text)) textInputLayout.error = "Invalid Name"
                else {
                    textInputLayout.error = null
                    textInputLayout.isErrorEnabled = false
                }
            }
        })
        binding.email.editText?.addTextChangedListener(object : InputValidation(binding.email) {
            override fun validate(textInputLayout: TextInputLayout, text: String) {
                if (validEmail(text)) textInputLayout.error = "Invalid Email"
                else {
                    textInputLayout.error = null
                    textInputLayout.isErrorEnabled = false
                }
            }
        })
        binding.phoneNo.editText?.addTextChangedListener(object : InputValidation(binding.phoneNo) {
            override fun validate(textInputLayout: TextInputLayout, text: String) {
                if (!validPhoneNo(text)) textInputLayout.error = "Invalid PhoneNo"
                else {
                    textInputLayout.error = null
                    textInputLayout.isErrorEnabled = false
                }
            }
        })
        binding.id.editText?.addTextChangedListener(object : InputValidation(binding.id) {
            override fun validate(textInputLayout: TextInputLayout, text: String) {
                if (!validId(text)) textInputLayout.error = "Invalid id"
                else {
                    textInputLayout.error = null
                    textInputLayout.isErrorEnabled = false
                }
            }
        })
        binding.checkIn.editText?.addTextChangedListener(object : InputValidation(binding.checkIn) {
            override fun validate(textInputLayout: TextInputLayout, text: String) {
                if (validCheckInOut(textInputLayout)) textInputLayout.error = "Check-in can be done for next 3 days only"
                else {
                    textInputLayout.error = null
                    textInputLayout.isErrorEnabled = false
                }
            }
        })
        /*binding.checkOut.editText?.addTextChangedListener(object : InputValidation(binding.checkOut) {
            override fun validate(textInputLayout: TextInputLayout, text: String) {
                if (validCheckInOut(textInputLayout)) textInputLayout.error = "Invalid"
                else {
                    textInputLayout.error = null
                    textInputLayout.isErrorEnabled = false
                }
            }
        })*/

        binding.noOfDays.text = totalDays.toString().plus(" Night")
        totalRoomPrice = totalDays * roomModel?.roomPrice.toString().toInt()
        binding.totalRoomPrice.text = (totalRoomPrice).toString().plus(" /-")
        binding.totalPrice.text = (totalRoomPrice + 100 + 150).toString().plus(" /-")

        val myCalendar = Calendar.getInstance()
        val date = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd / MM / yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.checkIn.editText?.setText(sdf.format(myCalendar.time))
                val calendar = Calendar.getInstance()
                val simpleDateFormat = SimpleDateFormat("dd / MM / yyyy", Locale.US)
                val date = simpleDateFormat.format(calendar.time).toString()
                val dateCheckIn = binding.checkIn.editText?.text.toString()
                totalDays = calculateDate(date, dateCheckIn)
                if (totalDays >= 1) {
                        binding.noOfDays.text = totalDays.toString().plus(" Night")
                        totalRoomPrice = totalDays * roomModel?.roomPrice.toString().toInt()
                        binding.totalRoomPrice.text = (totalRoomPrice).toString().plus(" /-")
                        binding.totalPrice.text = (totalRoomPrice + 100 + 150).toString().plus(" /-")
                }

            }

        /*val date1 = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd / MM / yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.checkOut.editText?.setText(sdf.format(myCalendar.time))
            val dateCheckIn = binding.checkIn.editText?.text.toString()
            val dateCheckOut = binding.checkOut.editText?.text.toString()
            if (dateCheckIn.isNotEmpty() && dateCheckOut.isNotEmpty()) {
                totalDays = calculateDate(dateCheckIn, dateCheckOut)
                if (totalDays >= 1) {
//                    binding.checkIn.error = null
//                    binding.checkIn.isErrorEnabled = false
//                    binding.checkOut.error = null
//                    binding.checkOut.isErrorEnabled = false
                    binding.noOfDays.text = totalDays.toString().plus(" Night")
                    totalRoomPrice = totalDays * roomModel?.roomPrice.toString().toInt()
                    binding.totalRoomPrice.text = (totalRoomPrice).toString().plus(" /-")
                    binding.totalPrice.text = (totalRoomPrice + 100 + 150).toString().plus(" /-")
                } else {
                    totalDays = 0
                    binding.checkIn.error = "Invalid  Dates Selection"
                    binding.checkOut.error = "Invalid  Dates Selection"
                }
            }
        }*/

        binding.checkIn.editText?.setOnClickListener {
            DatePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog, date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]).show()
        }


        binding.payment.setOnClickListener {
            if (binding.firstName.isErrorEnabled ||
                    binding.lastName.isErrorEnabled ||
                    binding.id.isErrorEnabled ||
                    binding.phoneNo.isErrorEnabled ||
                    validCheckInOut(binding.checkIn) ||
                    binding.checkIn.isErrorEnabled) return@setOnClickListener

            val firstName = binding.firstName.editText?.text.toString()
            val lastName = binding.lastName.editText?.text.toString()
            val id = binding.id.editText?.text.toString()
            val phoneNumber = binding.phoneNo.editText?.text.toString()
            val email = binding.email.editText?.text.toString()
            val checkIn = binding.checkIn.editText?.text.toString()
            val totalPrice = binding.totalPrice.text.toString()
            val totalRoomPrice = binding.totalRoomPrice.text.toString()
            val security = binding.security.text.toString()
            val service = binding.service.text.toString()
            val noOfDays = binding.noOfDays.text.toString()
            val roomName = binding.roomName.text.toString()

            val model = roomModel?.let { it1 ->
                hotel?.let { it2 ->
                    HistoryModel(userId = "",firstName = firstName,lastName = lastName, id = id
                        , email = email,phoneNo = phoneNumber,checkIn =  checkIn, totalPrice = totalPrice
                        ,security = security, service = service, totalRoomPrice = totalRoomPrice
                        ,noOfDays = noOfDays,roomImage = it1.roomImage, roomName = roomModel.roomName
                        , roomPrice = roomModel.roomPrice, roomNumber = "",hotelImage = hotel.hotelImage
                        , hotelName = it2.hotelName,bookingDate =  "", bookingTime = "",bookingState =  "")
                }
            }

            val intent = Intent(applicationContext, PaymentActivity::class.java)
            intent.putExtra("historyModel", model)
            /*intent.putExtra("lastName", lastName)
            intent.putExtra("id", id)
            intent.putExtra("phoneNo", phoneNumber)
            intent.putExtra("email", email)
            intent.putExtra("checkIn", checkIn)
            intent.putExtra("totalPrice", totalPrice)
            intent.putExtra("noOfDays", noOfDays)
            intent.putExtra("hotelName", hotel?.hotelName)
            intent.putExtra("hotelImage", hotel?.hotelImage)
            intent.putExtra("roomName", roomName)
            intent.putExtra("roomImage", roomModel?.roomImage)
            intent.putExtra("roomPrice", roomModel?.roomPrice)
            intent.putExtra("totalRoomPrice", totalRoomPrice)
            intent.putExtra("security", security)
            intent.putExtra("service", service)*/
            startActivity(intent)
        }
        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun validCheckInOut(textInputLayout: TextInputLayout) : Boolean {
        val input = textInputLayout.editText?.text.toString()
        return if (input.isEmpty()) {
            textInputLayout.error = "Field cannot be Empty"
            true
        } else false
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