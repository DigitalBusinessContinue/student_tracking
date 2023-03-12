package com.example.projecttrainning

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Student
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class StudentAdapter(private val studentList: List<Student>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    // Create a Cloud Storage reference from the app
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewStudentId: TextView = itemView.findViewById(R.id.txv_studentid)
        val textViewStudenName: TextView = itemView.findViewById(R.id.txv_name)
        val textViewStudentSurname: TextView = itemView.findViewById(R.id.txv_surname)
        val textViewStudentStatus: TextView = itemView.findViewById(R.id.txv_status)
        val btnStudentDetail: Button = itemView.findViewById(R.id.btn_studentdetail)

        val imv_profile : ImageView = itemView.findViewById(R.id.imv_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerlayout_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = studentList[position]
        holder.textViewStudentId.text = student.studentid
        holder.textViewStudenName.text = student.name
        holder.textViewStudentSurname.text = student.surname
        holder.textViewStudentStatus.text = student.status

        val imageRef = storageRef.child("students/" + student.email.substring(student.email.indexOf("cvc") + 3,student.email.indexOf("@")) + ".jpg")
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            // Image has been successfully downloaded
            val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            holder.imv_profile.setImageBitmap(image)

        }.addOnFailureListener { exception ->
            // There was an error downloading the image
        }

        holder.btnStudentDetail.setOnClickListener {
            val context = it.context
            val intent = Intent(context, TStudentDetailActivity::class.java)
            intent.putExtra("studentUID", student.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}
