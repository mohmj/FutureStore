package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.LastOrderAdapter
import com.example.futurestore.Models.LastOrderInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_last_oreders.*

class LastOredersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_oreders)

        var adapter=GroupAdapter<ViewHolder>()
        last_orders_activity_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        var uid= Firebase.auth.uid
        var reference=Firebase.database.getReference("users/$uid/orders")
        reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var order=it.getValue(LastOrderInformation::class.java)
                    if(order != null){
                        adapter.add(LastOrderAdapter(order))
                    }

                }
                last_orders_activity_recycler_view.adapter=adapter
                adapter.setOnItemClickListener { item, view ->
                    var intent= Intent(view.context,LastOrderShowActivity::class.java)
                    var intenty=item as LastOrderAdapter
                    intent.putExtra(Database().last_order,intenty.order)
                    startActivity(intent)
                }
            }

        })


    }
}