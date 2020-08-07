package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.futurestore.Models.UserInformation
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        var uid="123"
        var name="one two three"
        var email="123@onetwothree.ott"
        var phoneNumber="123123123"
        Firebase.database.getReference("TEST/name").removeValue()
    }
}