package com.example.futurestore.Services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Database (){
    var uid=Firebase.auth.uid
    var userReference=Firebase.database.getReference("users/$uid")
    var userName=Firebase.database.getReference("users/$uid/name")

    var category="category"
    var productInformation="product_information"
}