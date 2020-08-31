package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.ComputerAdapters.CoolerAdapter
import com.example.futurestore.Adapters.ProductAdapter
import com.example.futurestore.Models.Computer.ComputerCooler
import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.Services.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {

//    var adapter=GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val category=intent.getStringExtra(Database().category)
        supportActionBar?.title=intent.getStringExtra(Database().categoryTitle.toString())
        var adapter=GroupAdapter<ViewHolder>()
        product_activity_recycler_view.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)


//        when(category){
//            "water_cooler"->{
//              Firebase.database.getReference("products/computer/cooler").addListenerForSingleValueEvent(object:ValueEventListener{
//                  override fun onCancelled(error: DatabaseError) {
//                      TODO("Not yet implemented")
//                  }
//
//                  override fun onDataChange(snapshot: DataSnapshot) {
//                      snapshot.children.forEach {
//                          var thisCooler=it.getValue(ComputerCooler::class.java)
//                          if(thisCooler != null){
//                              if(thisCooler.type=="water_cooler"){
//                                  adapter.add(CoolerAdapter(thisCooler))
//                              }
//                          }
//                      }
//                      product_activity_recycler_view.adapter=adapter
//                      adapter.setOnItemClickListener(){item, view ->
//                          val intent= Intent(view.context,ProductShowActivity::class.java)
//                          val producty=item as CoolerAdapter
//                          intent.putExtra("one","water_cooler")
//                          intent.putExtra(Database().productInformation,producty.item)
//                          startActivity(intent)
//                      }
//                  }
//
//
//              })
//            }
//
//        }


        var reference=Firebase.database.getReference("products/$category")


        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach(){
                    val pro=it.getValue(ProductInformation::class.java)
                    if(pro != null){
                        adapter.add(ProductAdapter(pro))
                    }
                }


                product_activity_recycler_view.adapter=adapter
                adapter.setOnItemClickListener(){item, view ->
                    val intent= Intent(view.context,ProductShowActivity::class.java)
                    val producty=item as ProductAdapter
                    intent.putExtra(Database().productInformation,producty.info)
                    startActivity(intent)
                }
            }
        })
    }
}



