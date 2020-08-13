package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.CartAdapter
import com.example.futurestore.Adapters.ShowProductAdapter
import com.example.futurestore.Models.LastOrderInformation
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
import kotlinx.android.synthetic.main.activity_last_order_show.*

class LastOrderShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_order_show)
        var uid= Firebase.auth.uid
        var order_number=intent.getParcelableExtra<LastOrderInformation>(Database().last_order)?.order_number
        var time=intent.getParcelableExtra<LastOrderInformation>(Database().last_order)?.time
        var price=intent.getParcelableExtra<LastOrderInformation>(Database().last_order)?.total

        last_order_show_activity_order_number_text_view.text="Order number: "+order_number
        last_order_show_activity_order_time_text_view.text="Date: "+time
        last_order_show_activity_order_price_text_view.text="Price: "+price


        var status=intent.getParcelableExtra<LastOrderInformation>(Database().last_order)?.status.toString()
        when(status){
            Database().status_received->{
                last_order_activity_received_order_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_received_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
            }

            Database().status_preparing->{
                last_order_activity_received_order_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_received_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
                last_order_activity_preparing_order_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_preparing_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
            }
            Database().status_out_for_delivery->{
                last_order_activity_received_order_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_received_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
                last_order_activity_preparing_order_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_preparing_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
                last_order_activity_out_for_delivery_order_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_out_for_delivery_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
            }
            Database().status_deliverd->{
                last_order_activity_received_order_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_received_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
                last_order_activity_preparing_order_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_preparing_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
                last_order_activity_out_for_delivery_order_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_out_for_delivery_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
                last_order_activity_delivered_order_first_line.setBackgroundResource(R.drawable.default_line_dark)
                last_order_activity_delivered_order_oval.setBackgroundResource(R.drawable.default_oval_dark)
                last_order_activity_delivered_order_second_line.setBackgroundResource(R.drawable.default_line_dark)
            }
        }


        var adapter=GroupAdapter<ViewHolder>()
        last_order_show_activity_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        Firebase.database.getReference("users/$uid/orders/$order_number/products").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var product=it.getValue(ProductInformation::class.java)
                    if(product != null){
                        adapter.add(CartAdapter(product))
                    }
                }
                last_order_show_activity_recycler_view.adapter=adapter
            }

        })


    }
}