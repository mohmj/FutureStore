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
    var orders="orders"
    var last_order="last_order"
    var order_number="order_number"

    var buy_and_sale_accounts="buy_and_sale_accounts"
    var products="products"
    var computer="computer"
    var laptop="laptop"
    var programs_and_games="programs_and_games"
    var special_offers="special_offers"

    var update_account_intent="update_account_intent"
    var show_account_intent="show_account_intent"
}