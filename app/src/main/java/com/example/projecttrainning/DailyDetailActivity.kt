package com.example.projecttrainning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.projecttrainning.`class`.Daily
import com.example.projecttrainning.`class`.Trainer
import com.example.projecttrainning.databinding.ActivityDailyDetailBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class DailyDetailActivity : AppCompatActivity() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val dailyRef: DatabaseReference = database.getReference("dailys")
    private var dailyUID:String = "";
    private lateinit var binding: ActivityDailyDetailBinding
    var dailycount = 0
    private var studentEmail:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dailyUID = intent.getStringExtra("dailyUID").toString()

        dailycount = 0
        binding.edtNo.setText((dailycount+1).toString())


        dailyRef.orderByChild("id").equalTo(dailyUID).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dailySnapshot in dataSnapshot.children) {
                    val daily = dailySnapshot.getValue(Daily::class.java)
                    if (daily != null && daily.id == dailyUID) {
                        binding.edtNo.setText(daily.dailyid)
                        binding.edtDatetime.setText(daily.dailydate)
                        binding.edtMemo.setText(daily.dailyreport)
                        binding.edtRemark.setText(daily.dailyremark)
                        studentEmail = daily.studentemail
                        break
                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())

            }


        })

        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = simpleDateFormat.format(calendar.time)

        binding.edtDatetime.setText(currentDate)


    }

    fun funUpdate(view: View)
    {

        val daily = Daily()
        daily.id = dailyUID
        daily.dailyid = binding.edtNo.text.toString()
        daily.dailydate =  binding.edtDatetime.text.toString()
        daily.dailyreport = binding.edtMemo.text.toString()
        daily.dailyremark =binding.edtRemark.text.toString()
        daily.studentemail = studentEmail



        dailyRef.child(dailyUID).setValue(daily)
            .addOnSuccessListener {
                Toast.makeText(baseContext, "Update Success",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Update Fail",
                    Toast.LENGTH_SHORT).show()
            }



        finish()

    }

    fun funDelete(view: View)
    {
        dailyRef.child(dailyUID).removeValue()
        //re positions
        dailyRef.orderByChild("studentemail").startAt(studentEmail).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var i = 0
                for (dailySnapshot in dataSnapshot.children) {
                    val daily = dailySnapshot.getValue(Daily::class.java)
                    daily!!.dailyid = (i + 1).toString()
                    dailyRef.child(daily.id).setValue(daily)
                    i += 1
                }

                finish()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    fun funBack(view: View)
    {

        finish()
    }
}