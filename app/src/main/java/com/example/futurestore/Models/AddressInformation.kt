package com.example.futurestore.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AddressInformation (var lat:Double, var long:Double, var city:String, var locationType:String, var district:String, var street:String, var near:String, var time:String, var name:String, var phoneNumber:String):Parcelable {
    constructor():this(0.0,0.0,"","","","","","","","")
}