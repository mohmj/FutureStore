package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        auth= Firebase.auth

        reset_password_activity_button.setOnClickListener(){
            var email=reset_password_activity_email_edit_text.text.toString()
            if(email.isNotEmpty()){
                auth.sendPasswordResetEmail(email).addOnSuccessListener {
                    Toast.makeText(this,"تم الإرسال، الرجاء التحقق من البريد الإلكتروني",Toast.LENGTH_SHORT).show()
                    var signinIntent= Intent(this,SigninActivity::class.java)
                    signinIntent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(signinIntent)
                }.addOnFailureListener(){
                    Toast.makeText(this,"${it.message}",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"تأكد من تعبئة جميع البيانات",Toast.LENGTH_SHORT).show()
            }

        }

    }
}