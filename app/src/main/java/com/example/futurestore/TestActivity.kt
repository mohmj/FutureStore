package com.example.futurestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rey.material.app.Dialog
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

//        button.setOnClickListener(){
//            val mDialog = Dialog(this, R.layout.activity_cart_item)
//            mDialog.applyStyle(R.layout.activity_cart_item).title("Test").positiveAction("ok").negativeAction("Cancel").contentView(R.layout.activity_signin).show()
//        }

    }
}