package com.example.futurestore.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LastOrderInformation (var order_number:String, var status:String, var time:String, var total:String, var uid:String) :Parcelable {
    constructor():this("","","","","")
}