package com.example.futurestore.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AccountInformaion (var sellerId:String,var ID:String, var gameName:String, var descripe:String, var price:String, var imageLink:String, var phoneNumber:String):Parcelable {
    constructor():this("","","","","","","")
}