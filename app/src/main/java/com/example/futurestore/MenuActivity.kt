package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.futurestore.Models.UserInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val uid=Firebase.auth.uid
        
        if(uid==null) {
            menu_activity_name_text_view.text="تسجيل الدخول"
            menu_activity_name_text_view.setOnClickListener() {
                startActivity(Intent(this, SigninActivity::class.java))
            }
        }else{

        }
        menu_activity_profile_button.setOnClickListener(){
            startActivity(Intent(this,ProfileActivity::class.java))
        }
    }
}


