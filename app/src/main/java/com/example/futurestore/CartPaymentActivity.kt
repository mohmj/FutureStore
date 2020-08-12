package com.example.futurestore

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.futurestore.Models.AddressInformation
import com.example.futurestore.Models.LastOrderInformation
import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.Models.UserInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cart_payment.*
import java.time.LocalDateTime
import java.util.*

class CartPaymentActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_payment)
        var uid= Firebase.auth.uid.toString()
        var uuid= UUID.randomUUID().toString()
        Log.d("seya",uuid)
        Log.d("seya",uuid)
        var userReference=Firebase.database.getReference("users")
        var cartReference=Firebase.database.getReference("users/$uid/cart")
        var addressReference=Firebase.database.getReference("users/$uid/addresses")
        var lastOredersReference=Firebase.database.getReference("users/$uid/orders")
        var orders=Firebase.database.getReference("orders/$uid")
        var ordersUser=Firebase.database.getReference("users/$uid/orders").child(uuid)
        var locationType=intent.getStringExtra(Database().cartLocation)
        var total=intent.getStringExtra(Database().cartTotal)
        var random= Random().doubles()
        var payment_method="null"




        cart_payment_button.setOnClickListener() {
            if(payment_method=="null") {
                Toast.makeText(this,"You must choose payment method",Toast.LENGTH_SHORT).show()
            }else{
            var check_status=Database().status_deliverd
            Firebase.database.getReference("orders").addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        var check=it.getValue(LastOrderInformation::class.java)
                        if(check != null){
                            if(check.uid==uid){
                                check_status=check.status
                            }
                        }
                    }
                }

            })

            if(check_status != Database().status_deliverd) {
                Toast.makeText(this,"You can't order when last order is not delievird. Contact us for more information",Toast.LENGTH_LONG).show()
            }else {
                var time = LocalDateTime.now().toString()
                var timee = time
                cartReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var product = it.getValue(ProductInformation::class.java)
                            if (product != null) {
                                orders.child("products").child(product.name).setValue(product)
                                ordersUser.child("products").child(product.name).setValue(product)
                            }
                        }
                    }

                })
                addressReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var address = it.getValue(AddressInformation::class.java)
                            if (address != null) {
                                if (address.locationType == locationType) {
                                    orders.child("address").setValue(address)
                                    ordersUser.child("address").setValue(address)
                                }
                            }
                        }
                    }

                })
                userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var user = it.getValue(UserInformation::class.java)
                            if (user != null) {
                                if (user.uid == uid) {
                                    orders.child("user_information").setValue(user)
                                    ordersUser.child("user_information").setValue(user)
                                }
                            }
                        }
                    }
                })
                orders.child("total").setValue(total)
                orders.child(Database().status).setValue(Database().status_received)
                orders.child("time").setValue(time)
                orders.child("order_number").setValue(uuid)
                orders.child("uid").setValue(uid)

                ordersUser.child("total").setValue(total)
                ordersUser.child(Database().status).setValue(Database().status_received)
                ordersUser.child("time").setValue(time)
                ordersUser.child("order_number").setValue(uuid)
            }
        }
        }


        cart_payment_activity_cash_button.setOnClickListener(){
            payment_method="cash"
            cart_payment_activity_cash_button.setBackgroundResource(R.drawable.default_edit_text_on)
            cart_payment_activity_online_button.setBackgroundResource(R.drawable.default_edit_text_off)
        }
        cart_payment_activity_online_button.setOnClickListener(){
            payment_method="online"
            cart_payment_activity_cash_button.setBackgroundResource(R.drawable.default_edit_text_off)
            cart_payment_activity_online_button.setBackgroundResource(R.drawable.default_edit_text_on)
        }
    }
}