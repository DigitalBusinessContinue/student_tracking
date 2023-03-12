package com.example.projecttrainning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Doc
import com.example.projecttrainning.`class`.Student
import com.example.projecttrainning.`class`.Trainer
import com.example.projecttrainning.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val database = FirebaseDatabase.getInstance()
    val studentRef = database.getReference("students")
    val docRef = database.getReference("documents")
    private var studentEmail:String = "";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        studentEmail = intent.getStringExtra("studentEmail").toString()
        Toast.makeText(baseContext, "Student Email:$studentEmail",
            Toast.LENGTH_SHORT).show()

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

        val recyclerView: RecyclerView =  binding.recyclerviewDoc
        recyclerView.layoutManager = LinearLayoutManager(this)
        docRef.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (documentSnapshot in dataSnapshot.children) {

                    val documents : List<Doc> = dataSnapshot.children .map { dataSnapshot ->
                        dataSnapshot.getValue(Doc::class.java)!!
                    }
                    val adapter = DocAdapter(documents)
                    recyclerView.adapter = adapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"error",Toast.LENGTH_SHORT)
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

    fun funLogout(view: View) {
        val intent = Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }
}