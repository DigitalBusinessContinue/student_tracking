package com.example.projecttrainning

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.projecttrainning.`class`.Student

import com.example.projecttrainning.databinding.ActivityTstudentDetailBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class TStudentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTstudentDetailBinding
    val database = FirebaseDatabase.getInstance()
    val studentRef = database.getReference("students")
    public var studentEmail:String = ""
    private var studentUID:String = ""
    var exist : Boolean = false

    // Create a Cloud Storage reference from the app
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    private val PICK_IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTstudentDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBack.setOnClickListener {
            finish()
        }

        studentUID = intent.getStringExtra("studentUID").toString()

        //show data from firebase
        studentRef.orderByChild("id").equalTo(studentUID) .addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (studentSnapshot in dataSnapshot.children) {
                    val student = studentSnapshot.getValue(Student::class.java)
                    if (student != null) {
                        studentUID = student.id
                        studentEmail = student.email

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
                        val imageRef = storageRef.child("students/" + studentEmail.substring(studentEmail.indexOf("cvc") + 3,studentEmail.indexOf("@")) + ".jpg")
                        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                            // Image has been successfully downloaded
                            val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            binding.btnProfile.background = BitmapDrawable(resources, image)

                        }.addOnFailureListener { exception ->
                            // There was an error downloading the image
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
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



            //insert or update
            if(exist) //update
            {
                student.id = studentUID

                studentRef.child(studentUID).setValue(student)
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
            else //insert
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



        binding.btnDaily.setOnClickListener{
            val intent =  Intent(this, TStudentDailyActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            var st = studentEmail
            intent.putExtra("studentEmail", studentEmail)
            startActivity(intent)
            finish()
        }
        binding.btnPass.setOnClickListener {
            val student = Student()
            binding.txvStudentid.text =
                studentEmail.substring(studentEmail.indexOf("cvc") + 3, studentEmail.indexOf("@"))
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



            student.id = studentUID
            student.status = "ผ่าน"


            studentRef.child(studentUID).setValue(student) //update
                .addOnSuccessListener {
                    // Student object successfully updated
                    Toast.makeText(
                        baseContext, "Update Success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    // Failed to update student object
                    Toast.makeText(
                        baseContext, "Update Fail",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }
        binding.btnNopass.setOnClickListener {
            val student = Student()
            binding.txvStudentid.text =
                studentEmail.substring(studentEmail.indexOf("cvc") + 3, studentEmail.indexOf("@"))
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



            student.id = studentUID
            student.status = "ไม่ผ่าน"


            studentRef.child(studentUID).setValue(student) //update
                .addOnSuccessListener {
                    // Student object successfully updated
                    Toast.makeText(
                        baseContext, "Update Success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    // Failed to update student object
                    Toast.makeText(
                        baseContext, "Update Fail",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }
    }
}
