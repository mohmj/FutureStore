package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.futurestore.Models.UserInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    lateinit var auth:FirebaseAuth
    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth=Firebase.auth
        database=Firebase.database
        reset_password_activity_button.setOnClickListener(){
            val name=signup_activity_name_edit_text.text.toString()
            val email=signup_activitty_email_edit_text.text.toString()
            val phone=signup_activity_phone_number_edit_text.text.toString()
            val password=signup_activity_password_edit_text.text.toString()
            val passwordConfirm=signup_activity_confirm_password_edit_text.text.toString()

            if(name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()){
                if(password==passwordConfirm){
                    if(signup_activity_privacy_check_box.isChecked){
                        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                            var uid=auth.uid.toString()
                            var reference=database.reference.child("users/$uid")
                            reference.setValue(UserInformation(uid,name,email,phone))
                            Database().user_name=name
                            Toast.makeText(this,resources.getString(R.string.sign_up_successful),Toast.LENGTH_SHORT).show()

                            var mainIntent=Intent(this,MainActivity::class.java)
                            mainIntent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(mainIntent)

                        }.addOnFailureListener(){
                            Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this,resources.getString(R.string.you_must_accept_the_privacy),Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,resources.getString(R.string.password_does_not_match),Toast.LENGTH_SHORT).show()
                }
            }else{
               Toast.makeText(this,resources.getString(R.string.fill_your_data),Toast.LENGTH_SHORT).show()
            }
        }

        signup_activity_goto_signin_text_view.setOnClickListener(){
            var intent=Intent(this,SigninActivity::class.java)
            startActivity(intent)
        }


    }
}