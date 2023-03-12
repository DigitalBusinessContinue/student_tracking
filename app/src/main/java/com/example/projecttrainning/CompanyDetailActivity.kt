package com.example.projecttrainning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.projecttrainning.`class`.Student
import com.example.projecttrainning.`class`.Trainer
import com.example.projecttrainning.databinding.ActivityCompanyDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CompanyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyDetailBinding
    val database = FirebaseDatabase.getInstance()
    val trainerRef = database.getReference("trainers")
    private var studentEmail:String = ""
    private var trainerUID:String = ""
    var exist : Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        val window = this.window
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        trainerUID = intent.getStringExtra("trainerUID").toString()

        //show data from firebase
        trainerRef.orderByChild("id").equalTo(trainerUID) .addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (studentSnapshot in dataSnapshot.children) {
                    val trainer = studentSnapshot.getValue(Trainer::class.java)
                    if (trainer != null) {
                        trainerUID = trainer.id
                        binding.edtTrainercompany.setText(trainer.trainercompany)
                        binding.edtTraineraddress.setText(trainer.traineraddress)
                        binding.edtTrainertype.setText(trainer.trainertype)
                        binding.edtTrainerservice.setText(trainer.trainerservice)
                        binding.edtTrainercapasity.setText(trainer.trainercapasity)
                        binding.edtTrainertel.setText(trainer.trainertel)

                        exist = true

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.btnBack.setOnClickListener {
            finish()
        }


    }
}