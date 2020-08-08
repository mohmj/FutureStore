package com.example.futurestore.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProductInformation(var name:String,var category:String, var price:String, var quantity:Int, var imageLink:String,var description: String):
    Parcelable {
    constructor():this("","","",1,"","")
}