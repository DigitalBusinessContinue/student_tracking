package com.example.projecttrainning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Daily
import com.example.projecttrainning.`class`.Student
import com.example.projecttrainning.databinding.ActivityDailyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DailyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDailyBinding
    val database = FirebaseDatabase.getInstance()
    val dailyRef = database.getReference("dailys")
    val studentRef = database.getReference("students")
    private var studentEmail:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        studentEmail = intent.getStringExtra("studentEmail").toString()

        val recyclerView: RecyclerView =  binding.recyclerviewDaily
        recyclerView.layoutManager = LinearLayoutManager(this)

        dailyRef.orderByChild("studentemail").equalTo(studentEmail).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dailys : List<Daily> = dataSnapshot.children .map { dataSnapshot ->
                    dataSnapshot.getValue(Daily::class.java)!!
                }
                val adapter = DailyAdapter(dailys)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.btnNewdaily.setOnClickListener {
            val intent = Intent(this, DailyAddActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("studentEmail", studentEmail)
            startActivity(intent)
            finish()
        }


        studentRef.orderByChild("email").equalTo(studentEmail) .addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (studentSnapshot in dataSnapshot.children) {
                    val student = studentSnapshot.getValue(Student::class.java)
                    if (student != null) {
                        binding.txvNameheader.text = student.name + " " + student.surname
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }



    fun funMain(view: View) {
        val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("studentEmail", studentEmail)
        startActivity(intent)
        finish()
    }

    fun funDailyReport(view: View) {
        val intent = Intent(this, DailyActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("studentEmail", studentEmail)
        startActivity(intent)
        finish()
    }

    fun funCompany(view: View) {
        val intent = Intent(this, CompanyActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("studentEmail", studentEmail)
        startActivity(intent)
        finish()
    }

    fun funMe(view: View) {
        val intent = Intent(this, MeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("studentEmail", studentEmail)
        startActivity(intent)
        finish()
    }

    fun funNew(view: View) {
        val intent = Intent(this, DailyDetailActivity::class.java)
        intent.putExtra("studentEmail", studentEmail)
        startActivity(intent)
        finish()
    }



}