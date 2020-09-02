package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.CartAdapter
import com.example.futurestore.Models.AddressInformation
import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_cart_summary.*
import java.math.RoundingMode

class CartSummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_summary)

        var uid= Firebase.auth.uid
        var adapter=GroupAdapter<ViewHolder>()
        cart_summary_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        var total=intent.getStringExtra(Database().cartTotal)
        var subtotal=(total!!.toDouble()*0.85).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
        var VAT=(total!!.toDouble()*0.15).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
        var locationType=intent.getParcelableExtra<AddressInformation>(Database().cartLocation)?.locationType.toString()
        var locationNumber=intent.getParcelableExtra<AddressInformation>(Database().cartLocation)?.locationNumber.toString()
        var city=intent.getParcelableExtra<AddressInformation>(Database().cartLocation)?.city
        var district=intent.getParcelableExtra<AddressInformation>(Database().cartLocation)?.district
        var street=intent.getParcelableExtra<AddressInformation>(Database().cartLocation)?.street

        cart_summary_activity_name_text_view.text=intent.getParcelableExtra<AddressInformation>(Database().cartLocation)?.name
        cart_summary_activity_phone_number_text_view.text=intent.getParcelableExtra<AddressInformation>(Database().cartLocation)?.phoneNumber
        cart_summary_activity_location_text_view.text="$city, $district, $street"
        cart_summary_activity_time_text_view.text=intent.getParcelableExtra<AddressInformation>(Database().cartLocation)?.time
        cart_summary_activity_sub_total_text_view.text="Subtotal: $subtotal"
        cart_summary_activity_VAT_text_view.text="VAT: $VAT"
        cart_summary_activity_total_text_view.text="Total: $total"

        Firebase.database.getReference("users/$uid/cart").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var item=it.getValue(ProductInformation::class.java)
                    if(item != null){
                        adapter.add(CartAdapter(item))
                    }
                }
                cart_summary_recycler_view.adapter=adapter
            }
        })

        cart_summery_activity_button.setOnClickListener(){
            var intent= Intent(this,CartPaymentActivity::class.java)
            intent.putExtra(Database().cartTotal,total)
            intent.putExtra(Database().cartLocation,locationNumber)
            startActivity(intent)
        }
    }
}