package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.ComputerAdapters.CoolerAdapter
import com.example.futurestore.Models.Computer.ComputerCooler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_product_cooler.*
import kotlinx.android.synthetic.main.activity_show_product_cooler.*

class ProductCoolerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_cooler)

        var type_of_cooler=intent.getStringExtra("type_of_cooler")
        var adapter= GroupAdapter<ViewHolder>()
        product_activity_recycler_view_cooler.layoutManager=
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        Firebase.database.getReference("products/computer/cooler").addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var thisCooler=it.getValue(ComputerCooler::class.java)
                    if(thisCooler != null){
                        if(thisCooler.type==type_of_cooler){
                            adapter.add(CoolerAdapter(thisCooler))
                        }
                    }
                }
                product_activity_recycler_view_cooler.adapter=adapter
                adapter.setOnItemClickListener { item, view ->
                    var intent= Intent(view.context,ShowProductCoolerActivity::class.java)
                    var intenty=item as CoolerAdapter
                    intent.putExtra("cooler_put",intenty.item)
                    startActivity(intent)
                }
            }

        })

    }
}