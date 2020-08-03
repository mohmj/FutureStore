package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.futurestore.Models.UserInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_personal_data.*

class PersonalDataActivity : AppCompatActivity() {

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        auth= Firebase.auth
        var uid=auth.uid

        var name=""
        var  email=""
        var phoneNumber=""

        if(uid==null){

        }else{
            var reference=Firebase.database.getReference("users")
            reference.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach(){
                        var userData=it.getValue(UserInformation::class.java)
                        if(userData?.uid==uid){
                            if(userData != null) {
                            personal_data_activity_name_edit_text.setText(userData.name)
                                name=userData.name
                            personal_data_activity_email_edit_text.setText(userData.email)
                                email=userData.email
                            personal_data_activity_phone_number_edit_text.setText(userData.phoneNumber)
                                phoneNumber=userData.phoneNumber
                            }
                        }
                    }
                }
            })

            personal_data_activity_button.setOnClickListener(){
                name=personal_data_activity_name_edit_text.text.toString()
                email=personal_data_activity_email_edit_text.text.toString()
                phoneNumber=personal_data_activity_phone_number_edit_text.text.toString()

                var reference=Firebase.database.getReference("users/$uid")
                auth.currentUser?.updateEmail(email)?.addOnSuccessListener {
                    reference.setValue(UserInformation(uid,name,email,phoneNumber)).addOnSuccessListener {
                        Toast.makeText(this,resources.getString(R.string.the_data_was_updated_successfully),Toast.LENGTH_SHORT).show()
                        var intent= Intent(this,ProfileActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }?.addOnFailureListener {
                  Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}