package com.example.projecttrainning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Trainer
import com.example.projecttrainning.databinding.ActivityCompanyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyBinding
    val database = FirebaseDatabase.getInstance()
    val trainerRef = database.getReference("trainers")
    private var studentEmail:String = ""
    private var companyUID:String = ""
    var exist : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        studentEmail = intent.getStringExtra("studentEmail").toString()
        val recyclerView: RecyclerView =  binding.recyclerviewTrainer
        recyclerView.layoutManager = LinearLayoutManager(this)


        trainerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val trainers : List<Trainer> = dataSnapshot.children .map { dataSnapshot ->
                    dataSnapshot.getValue(Trainer::class.java)!!
                }
                val adapter = TrainerAdapter(trainers)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        binding.edtFindtrainercompany.doAfterTextChanged {
            var str : String? = binding.edtFindtrainercompany.text.toString()
            if(str == null || str == "")
            {

                trainerRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val trainers : List<Trainer> = dataSnapshot.children .map { dataSnapshot ->
                            dataSnapshot.getValue(Trainer::class.java)!!
                        }
                        val adapter = TrainerAdapter(trainers)
                        recyclerView.adapter = adapter
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            }
            else

                trainerRef.orderByChild("trainercompany").startAt(str).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val trainers : List<Trainer> = dataSnapshot.children .map { dataSnapshot ->
                            dataSnapshot.getValue(Trainer::class.java)!!
                        }
                        val adapter = TrainerAdapter(trainers)
                        recyclerView.adapter = adapter
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
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


}


