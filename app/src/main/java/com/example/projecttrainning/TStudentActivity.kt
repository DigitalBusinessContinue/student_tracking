package com.example.projecttrainning

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Daily
import com.example.projecttrainning.`class`.Student
import com.example.projecttrainning.`class`.Trainer
import com.example.projecttrainning.databinding.ActivityTstudentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class TStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTstudentBinding
    private var teacherEmail:String = "";
    val database = FirebaseDatabase.getInstance()
    val studentRef = database.getReference("students")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTstudentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        teacherEmail = intent.getStringExtra("teacherEmail").toString()


        val recyclerView: RecyclerView =  binding.recyclerviewStudent
        recyclerView.layoutManager = LinearLayoutManager(this)

        studentRef.orderByChild("studentid") .addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val students: MutableList<Student> = mutableListOf<Student>()
                for (studentSnapshot in dataSnapshot.children) {
                    studentSnapshot.getValue<Student>()?.let { students.add(it) }
                }

                val adapter = StudentAdapter(students)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })





        val options = arrayOf("ทั้งหมด" ,"2562", "2563" , "2564 ")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Do something when an option is selected
                if(position == 0 ) //ทั้งหมด
                {
                    studentRef.orderByChild("studentid").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            val students: MutableList<Student> = mutableListOf<Student>()
                            for (studentSnapshot in dataSnapshot.children) {

                                    studentSnapshot.getValue<Student>()?.let { students.add(it) }


                            }
                            val adapter = StudentAdapter(students)
                            recyclerView.adapter = adapter
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }
                else if(position == 1) //2562
                {
                    studentRef.orderByChild("studentid").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            val students: MutableList<Student> = mutableListOf<Student>()
                            for (studentSnapshot in dataSnapshot.children) {
                                var studentidtemp = studentSnapshot.getValue<Student>()?.studentid
                                if( studentidtemp?.indexOf("62") == 0)
                                {
                                    studentSnapshot.getValue<Student>()?.let { students.add(it) }
                                }

                            }
                            val adapter = StudentAdapter(students)
                            recyclerView.adapter = adapter
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }
                else if(position == 2) //2563
                {
                    studentRef.orderByChild("studentid").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            val students: MutableList<Student> = mutableListOf<Student>()
                            for (studentSnapshot in dataSnapshot.children) {
                                var studentidtemp =  studentSnapshot.getValue<Student>()?.studentid
                                if( studentidtemp?.indexOf("63") == 0)
                                {
                                    studentSnapshot.getValue<Student>()?.let { students.add(it) }
                                }

                            }
                            val adapter = StudentAdapter(students)
                            recyclerView.adapter = adapter
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }
                else if(position == 3) //2564
                {
                    studentRef.orderByChild("studentid").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            val students: MutableList<Student> = mutableListOf<Student>()
                            for (studentSnapshot in dataSnapshot.children) {
                                var studentidtemp =  studentSnapshot.getValue<Student>()?.studentid
                                if( studentidtemp?.indexOf("64") == 0)
                                {
                                    studentSnapshot.getValue<Student>()?.let { students.add(it) }
                                }

                            }
                            val adapter = StudentAdapter(students)
                            recyclerView.adapter = adapter
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val options2 = arrayOf("ทั้งหมด" , "กำลังฝึก", "ผ่าน" , "ไม่ผ่าน")
        val adapter2 = ArrayAdapter(this, R.layout.simple_spinner_item, options2)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter = adapter2

        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0 ) //ทั้งหมด
                {
                    studentRef.orderByChild("studentid").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            val students: List<Student> = dataSnapshot.children.map { dataSnapshot ->
                                dataSnapshot.getValue(Student::class.java)!!
                            }
                            val adapter = StudentAdapter(students)
                            recyclerView.adapter = adapter
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
                else if(position == 1) //กำลังฝึก
                {
                    studentRef.orderByChild("status").equalTo("กำลังฝึก").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            val students: List<Student> = dataSnapshot.children.map { dataSnapshot ->
                                dataSnapshot.getValue(Student::class.java)!!
                            }
                            val adapter = StudentAdapter(students)
                            recyclerView.adapter = adapter
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    })
                }
                else if(position == 2) //ผ่าน
                {
                    studentRef.orderByChild("status").equalTo("ผ่าน").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            val students: List<Student> = dataSnapshot.children.map { dataSnapshot ->
                                dataSnapshot.getValue(Student::class.java)!!
                            }
                            val adapter = StudentAdapter(students)
                            recyclerView.adapter = adapter
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    })
                }
                else if(position == 3) //กำลังฝึก
                {
                    studentRef.orderByChild("status").equalTo("ไม่ผ่าน").addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            val students: List<Student> = dataSnapshot.children.map { dataSnapshot ->
                                dataSnapshot.getValue(Student::class.java)!!
                            }
                            val adapter = StudentAdapter(students)
                            recyclerView.adapter = adapter
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }


        binding.edtFindstudent.doAfterTextChanged {
            var str : String? = binding.edtFindstudent.text.toString()
            if(str == null || str == "")
            {
                studentRef.addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val students : List<Student> = dataSnapshot.children .map { dataSnapshot ->
                            dataSnapshot.getValue(Student::class.java)!!
                        }
                        val adapter = StudentAdapter(students)
                        recyclerView.adapter = adapter
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle error
                    }
                })

            }
            else {
                studentRef.orderByChild("name").startAt(str).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                        val students: List<Student> = dataSnapshot.children.map { dataSnapshot ->
//                            dataSnapshot.getValue(Student::class.java)!!
//                        }
//                        val adapter = StudentAdapter(students)
//                        recyclerView.adapter = adapter

                        val students = mutableListOf<Student>()
                        for (studentSnapshot in dataSnapshot.children) {
                            val student = studentSnapshot.getValue(Student::class.java)
                            if (student != null) {
                                    if(student.name.toString().indexOf(str) != -1) {
                                    students.add(student)
                                }
                            }

                        }
                        val adapter = StudentAdapter(students)
                        recyclerView.adapter = adapter
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle error
                    }
                })
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



}