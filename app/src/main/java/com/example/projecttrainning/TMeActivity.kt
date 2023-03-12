package com.example.projecttrainning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.projecttrainning.`class`.Student
import com.example.projecttrainning.`class`.Teacher
import com.example.projecttrainning.databinding.ActivityTmeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TMeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTmeBinding
    private var teacherEmail:String = ""
    private var teacherUID:String = ""
    val database = FirebaseDatabase.getInstance()
    val teacherRef = database.getReference("teachers")
    val docRef = database.getReference("documents")
    var exist : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTmeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        teacherEmail = intent.getStringExtra("teacherEmail").toString()

        //show data from firebase
        teacherRef.orderByChild("email").equalTo(teacherEmail) .addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (teacherSnapshot in dataSnapshot.children) {
                    val teacher = teacherSnapshot.getValue(Teacher::class.java)
                    if (teacher != null) {
                        binding.edtName.setText(teacher.name)
                        binding.edtSurname.setText(teacher.surname)
                        binding.edtTel.setText(teacher.tel)
                        teacherUID = teacher.id
                        binding.txvStudentid.text = teacher.id
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        binding.btnSave.setOnClickListener {

            val teacher = Teacher()

            teacher.studentid = binding.txvStudentid.text as String

            teacher.name = binding.edtName.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            teacher.surname = binding.edtSurname.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            teacher.tel = binding.edtTel.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }


            exist  = true
            //insert or update
            if(exist) //update
            {
                teacher.id = teacherUID

                teacherRef.child(teacherUID).setValue(teacher)
                    .addOnSuccessListener {
                        // Student object successfully updated
                        Toast.makeText(baseContext, "Update Success",
                            Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        // Failed to update student object
                        Toast.makeText(baseContext, "Update Fail",
                            Toast.LENGTH_SHORT).show()
                    }
            }



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