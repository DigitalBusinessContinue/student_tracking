package com.example.projecttrainning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projecttrainning.`class`.Trainer
import com.example.projecttrainning.databinding.ActivityTcompanyDetailBinding
import com.example.projecttrainning.databinding.ActivityTcompanyNewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TCompanyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTcompanyDetailBinding
    val database = FirebaseDatabase.getInstance()

    private val trainerRef = database.getReference("trainers")

    private var studentEmail:String = ""
    private var trainerUID:String = ""
    var exist : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTcompanyDetailBinding.inflate(layoutInflater)
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
                // Failed to read value
            }
        })

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave2.setOnClickListener {
            //add new trainer company

            val trainer = Trainer()
            trainer.id = trainerUID
            trainer.trainercapasity = binding.edtTrainercapasity.text.toString()
            trainer.trainerservice =binding.edtTrainerservice.text.toString()
            trainer.trainertel = binding.edtTrainertel.text.toString()
            trainer.traineraddress = binding.edtTraineraddress.text.toString()
            trainer.trainertype = binding.edtTrainertype.text.toString()
            trainer.trainercompany = binding.edtTrainercompany.text.toString()


            trainerRef.child(trainerUID).setValue(trainer)
            finish()
        }

    }
}