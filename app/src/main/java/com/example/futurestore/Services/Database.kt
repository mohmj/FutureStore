package com.example.futurestore.Services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Database (){
    var uid=Firebase.auth.uid
    var userReference=Firebase.database.getReference("users/$uid")
//    var userName=Firebase.database.getReference("users/$uid/name")

    var category="category"
    var categoryTitle="category_title"
    var productInformation="product_information"
    var user_name:String=""
    var contactMessagesPath="contact/$uid/messages"
    var cartIntent="cart_intent"
    var wishListIntent="wish_list_intent"
    var updateLocation="update_location"
    val cartTotal="cart_total"
    var cartLocation="cart_location"
    var total:Double=0.0
    var status="status"
    var status_received="Received_order"
    var status_preparing="Preparing_Order"
    var status_out_for_delivery="Out_for_Delivery"
    var status_deliverd="Delivered"
    var last_order="last_order"
    var order_number="order_number"
}