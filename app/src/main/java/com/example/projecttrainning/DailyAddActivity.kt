package com.example.projecttrainning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.projecttrainning.`class`.Daily
import com.example.projecttrainning.`class`.Student
import com.example.projecttrainning.databinding.ActivityDailyAddBinding
import com.example.projecttrainning.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class DailyAddActivity : AppCompatActivity() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val dailyRef: DatabaseReference = database.getReference("dailys")
    private var studentEmail:String = "";
    private lateinit var binding: ActivityDailyAddBinding
    var dailycount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        studentEmail = intent.getStringExtra("studentEmail").toString()

        dailycount = 0
        binding.edtNo.setText((dailycount+1).toString())


        dailyRef.orderByChild("studentemail").equalTo(studentEmail).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val count = dataSnapshot.childrenCount
                Log.d("TAG", "Number of Daily objects: $count")
                dailycount = count.toInt()
                binding.edtNo.setText((dailycount+1).toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
                dailycount = 0
                binding.edtNo.setText((dailycount+1).toString())
            }


        })

        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = simpleDateFormat.format(calendar.time)

        binding.edtDatetime.setText(currentDate)


    }

    fun funAdd(view: View)
    {


        val newDailyRef = dailyRef.push()
        val daily = Daily()
        daily.id = newDailyRef.key!!.toString()
        daily.dailyid = binding.edtNo.text.toString()
        daily.dailydate =  binding.edtDatetime.text.toString()
        daily.dailyreport = binding.edtMemo.text.toString()
        daily.dailyremark =binding.edtRemark.text.toString()
        daily.studentemail = studentEmail

        newDailyRef.setValue(daily)

        val intent = Intent(this, DailyActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("studentEmail", studentEmail)
        startActivity(intent)
        finish()

    }

    fun funBack(view: View)
    {
        val intent = Intent(this, DailyActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("studentEmail", studentEmail)
        startActivity(intent)
        finish()
    }
}