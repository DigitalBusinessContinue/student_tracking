package com.example.projecttrainning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.projecttrainning.`class`.Daily
import com.example.projecttrainning.`class`.Trainer
import com.example.projecttrainning.databinding.ActivityTcompanyNewBinding
import com.google.firebase.database.FirebaseDatabase

class TCompanyNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTcompanyNewBinding
    val database = FirebaseDatabase.getInstance()
    val trainerRef = database.getReference("trainers")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTcompanyNewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnNew.setOnClickListener {
            //add new trainer company
            val newTrainerRef = trainerRef.push()
            val trainer = Trainer()
            trainer.id = newTrainerRef.key!!.toString()
            trainer.trainercapasity = binding.edtTrainercapasity.text.toString()
            trainer.trainerservice =binding.edtTrainerservice.text.toString()
            trainer.trainertel = binding.edtTrainertel.text.toString()
            trainer.traineraddress = binding.edtTraineraddress.text.toString()
            trainer.trainertype = binding.edtTrainertype.text.toString()
            trainer.trainercompany = binding.edtTrainercompany.text.toString()


            newTrainerRef.setValue(trainer)
            finish()


        }

        binding.btnBack.setOnClickListener {
            finish()
        }

    }
}