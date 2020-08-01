package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth= Firebase.auth

        reset_password_activity_button.setOnClickListener(){
        var email=reset_password_activity_email_edit_text.text.toString()
        var password=signin_activity_password_edit_text.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    Toast.makeText(this,"تم تسجيل الدخول بنجاح",Toast.LENGTH_SHORT).show()
                    var mainIntent=Intent(this,MainActivity::class.java)
                    mainIntent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(mainIntent)
                }.addOnFailureListener(){
                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"تأكد من تعبئة جميع البيانات",Toast.LENGTH_SHORT).show()
            }
        }

        signin_activity_goto_signup_text_view.setOnClickListener(){
            var intent= Intent(this,SignupActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        signin_activity_forgetMyPassword_text_view.setOnClickListener(){
            startActivity(Intent(this,ResetPasswordActivity::class.java))
        }



    }
}