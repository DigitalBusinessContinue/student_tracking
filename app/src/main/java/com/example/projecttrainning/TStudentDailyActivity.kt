package com.example.projecttrainning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Daily
import com.example.projecttrainning.databinding.ActivityDailyBinding
import com.example.projecttrainning.databinding.ActivityTstudentDailyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TStudentDailyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTstudentDailyBinding
    val database = FirebaseDatabase.getInstance()
    val dailyRef = database.getReference("dailys")
    private var studentEmail:String = ""
    private var studentUID:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTstudentDailyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        studentEmail = intent.getStringExtra("studentEmail").toString()

        val recyclerView: RecyclerView =  binding.recyclerviewDaily
        recyclerView.layoutManager = LinearLayoutManager(this)

        dailyRef.orderByChild("studentemail").equalTo(studentEmail).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dailys : List<Daily> = dataSnapshot.children .map { dataSnapshot ->
                    dataSnapshot.getValue(Daily::class.java)!!
                }
                val adapter = DailyAdapter(dailys)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}