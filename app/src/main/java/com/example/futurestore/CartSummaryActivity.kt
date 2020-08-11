package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.futurestore.Models.Location
import com.example.futurestore.Services.Database

class CartSummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_summary)

        var oneTotal=intent.getStringExtra(Database().cartTotal)
        var street=intent.getParcelableExtra<Location>(Database().cartLocation)?.street
        Log.d("seya",oneTotal.toString())
        Log.d("Seya",street.toString())
    }
}