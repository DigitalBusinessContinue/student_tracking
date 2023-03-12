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
import com.example.projecttrainning.`class`.Teacher
import com.example.projecttrainning.databinding.ActivityTmainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTmainBinding
    private var teacherEmail:String = "";
    val database = FirebaseDatabase.getInstance()
    val teacherRef = database.getReference("teachers")
    val docRef = database.getReference("documents")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTmainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        teacherEmail = intent.getStringExtra("teacherEmail").toString()

        //show data from firebase
        teacherRef.orderByChild("email").equalTo(teacherEmail) .addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (studentSnapshot in dataSnapshot.children) {
                    val Teacher = studentSnapshot.getValue(Student::class.java)
                    if (Teacher != null) {
                        binding.txvNameheader.text = Teacher.name + " " + Teacher.surname
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        binding.button.setOnClickListener {
            val intent = Intent(this, TStudentActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("teacherEmail", teacherEmail)
            startActivity(intent)
            finish()
        }

        binding.button6.setOnClickListener {
            val intent = Intent(this, TCompanyActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("teacherEmail", teacherEmail)
            startActivity(intent)
            finish()
        }

    }

    fun funMain(view: View) {
        val intent = Intent(this, TMainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("teacherEmail", teacherEmail)
        startActivity(intent)
        finish()
    }

    fun funDailyReport(view: View) {
        val intent = Intent(this, TStudentActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("teacherEmail", teacherEmail)
        startActivity(intent)
        finish()
    }

    fun funCompany(view: View) {
        val intent = Intent(this, TCompanyActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("teacherEmail", teacherEmail)
        startActivity(intent)
        finish()
    }

    fun funMe(view: View) {
        val intent = Intent(this, TMeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("teacherEmail", teacherEmail)
        startActivity(intent)
        finish()
    }

    fun funLogout(view: View) {
        val intent = Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }


}