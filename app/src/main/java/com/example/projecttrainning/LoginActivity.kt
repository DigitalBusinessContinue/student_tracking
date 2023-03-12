package com.example.projecttrainning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.projecttrainning.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var studentEmail:String = "0000000000";
    private lateinit var binding:ActivityLoginBinding

    private lateinit var auth : FirebaseAuth

    var TAG = "Student"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
    }

    fun funStudentLogin(view: View) {
        var email:String = binding.edtEmail.text.toString()
        var password = binding.edtPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        studentEmail = user.email.toString()

                        if(studentEmail == "a@a.com") //teacher
                        {
                            val intent = Intent(this, TMainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            intent.putExtra("teacherEmail", studentEmail)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            intent.putExtra("studentEmail", studentEmail)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    studentEmail = "0000000000"
                }
            }

    }

    fun funTeacherLogin(view: View) {
        var email:String = binding.edtEmail.text.toString()
        var password = binding.edtPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        studentEmail = user.email.toString()


                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    studentEmail = "0000000000"
                }
            }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){

        }
    }

}