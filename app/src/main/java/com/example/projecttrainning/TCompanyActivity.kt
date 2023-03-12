package com.example.projecttrainning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Trainer
import com.example.projecttrainning.databinding.ActivityTcompanyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class TCompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTcompanyBinding
    val database = FirebaseDatabase.getInstance()
    val trainerRef = database.getReference("trainers")
    private var teacherEmail:String = ""
    private var companyUID:String = ""
    var exist : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTcompanyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        teacherEmail = intent.getStringExtra("teacherEmail").toString()
        val recyclerView: RecyclerView =  binding.recyclerviewTrainer
        recyclerView.layoutManager = LinearLayoutManager(this)

        trainerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val trainers : List<Trainer> = dataSnapshot.children .map { dataSnapshot ->
                    dataSnapshot.getValue(Trainer::class.java)!!
                }
                val adapter = TTrainerAdapter(trainers)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        binding.btnNewcompany.setOnClickListener {
            val intent = Intent(this, TCompanyNewActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(intent)

        }

        binding.edtFindtrainercompany.doAfterTextChanged {
            var str : String? = binding.edtFindtrainercompany.text.toString()
            if(str == null || str == "")
            {
                trainerRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val trainers : List<Trainer> = dataSnapshot.children .map { dataSnapshot ->
                            dataSnapshot.getValue(Trainer::class.java)!!
                        }
                        val adapter = TTrainerAdapter(trainers)
                        recyclerView.adapter = adapter
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle error
                    }
                })

            }
            else
                trainerRef.orderByChild("trainercompany").startAt(str).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val trainers : List<Trainer> = dataSnapshot.children .map { dataSnapshot ->
                            dataSnapshot.getValue(Trainer::class.java)!!
                        }
                        val adapter = TTrainerAdapter(trainers)
                        recyclerView.adapter = adapter
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle error
                    }
                })
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



}