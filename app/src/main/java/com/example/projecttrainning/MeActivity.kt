package com.example.projecttrainning

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.projecttrainning.`class`.Student
import com.example.projecttrainning.databinding.ActivityMeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class MeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeBinding
    val database = FirebaseDatabase.getInstance()
    val studentRef = database.getReference("students")
    private var studentEmail:String = ""
    private var studentUID:String = ""
    var exist : Boolean = false

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        studentEmail = intent.getStringExtra("studentEmail").toString()


        studentRef.orderByChild("email").equalTo(studentEmail) .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (studentSnapshot in dataSnapshot.children) {
                    val student = studentSnapshot.getValue(Student::class.java)
                    if (student != null) {

                        studentUID = student.id
                        binding.txvStudentid.text = student.studentid

                        binding.edtName.setText(student.name)
                        binding.edtSurname.setText(student.surname)
                        binding.edtTel.setText(student.tel)
                        binding.edtTrainercompany.setText(student.trainercompany)
                        binding.edtTraineraddress.setText(student.traineraddress)
                        binding.edtTrainerpostion.setText(student.trainerposition)
                        binding.edtTrainerservice.setText(student.trainerservice)
                        binding.edtTrainner.setText(student.trainer)
                        binding.edtTrainertel.setText(student.trainertel)

                        binding.edtStudentcompany.setText(student.company)
                        binding.edtStudentposition.setText(student.position)
                        binding.edtStudentsalary.setText(student.salary)

                        exist = true

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


        binding.btnSave.setOnClickListener {

            val student = Student()
            binding.txvStudentid.text = studentEmail.substring(studentEmail.indexOf("cvc") + 3,studentEmail.indexOf("@"))
            student.studentid = binding.txvStudentid.text as String

            student.name = binding.edtName.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.surname = binding.edtSurname.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.tel = binding.edtTel.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }

            student.email = studentEmail

            student.trainercompany = binding.edtTrainercompany.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.traineraddress = binding.edtTraineraddress.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.trainerposition = binding.edtTrainerpostion.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.trainerservice = binding.edtTrainerservice.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.trainer = binding.edtTrainner.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.trainertel = binding.edtTrainertel.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.company = binding.edtStudentcompany.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.position = binding.edtStudentposition.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }
            student.salary = binding.edtStudentsalary.text.let {
                if (it.isNotEmpty()) it.toString().trim() else ""
            }



            if(exist)
            {
                student.id = studentUID

                studentRef.child(studentUID).setValue(student)
                    .addOnSuccessListener {
                        Toast.makeText(baseContext, "Update Success",
                            Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(baseContext, "Update Fail",
                            Toast.LENGTH_SHORT).show()
                    }
            }
            else
            {

                val newStudentRef = studentRef.push()
                var tempstr = ""

                student.id = newStudentRef.key!!.toString()
                student.status = "กำลังฝึก"

                newStudentRef.setValue(student)

                finish()
                startActivity(getIntent())
                exist = true
            }


        }


        binding.btnProfile.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
            }

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

        val imageRef = storageRef.child("students/" + studentEmail.substring(studentEmail.indexOf("cvc") + 3,studentEmail.indexOf("@")) + ".jpg")
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            binding.btnProfile.background = BitmapDrawable(resources, image)

        }.addOnFailureListener { exception ->
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri = data.data
            val inputStream = selectedImageUri?.let { contentResolver.openInputStream(it) }
            val selectedImage = BitmapFactory.decodeStream(inputStream)


            binding.btnProfile.background = BitmapDrawable(resources, selectedImage)
            val imageRef = storageRef.child("students/" + studentEmail.substring(studentEmail.indexOf("cvc") + 3,studentEmail.indexOf("@")) + ".jpg")
            val uploadTask = selectedImageUri?.let { imageRef.putFile(it) }
            uploadTask?.addOnSuccessListener {
            }?.addOnFailureListener {
            }
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

    fun funLogout(view: View) {
        val intent = Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }
}