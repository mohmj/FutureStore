package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.CartAdapter
import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)



        cart_activity_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        var adapter=GroupAdapter<ViewHolder>()
        val uid=Firebase.auth.uid
        var cartReference=Firebase.database.getReference("users/$uid/cart")
        getCart()





    }

    fun getCart(){
        var SUBTOTAL:Double=0.0
        var VAT:Double=0.0
        var DELIVERY:Double=0.0
        var TOTAL:Double=0.0
        var adapter=GroupAdapter<ViewHolder>()
        var uid=Firebase.auth.uid
        var cartReference=Firebase.database.getReference("users/$uid/cart")

        cartReference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var item=it.getValue(ProductInformation::class.java)
                    if(item != null){
                        var itemSubtotal=item.price.toDouble()
                        TOTAL+=itemSubtotal
                        SUBTOTAL=TOTAL*0.85
                        VAT=SUBTOTAL*0.15
                        Log.d("one",SUBTOTAL.toString())
                        adapter.add(CartAdapter(item))
                    }
                }
                cart_activity_sub_total_text_view.text="Subtotal: ${SUBTOTAL.toString()}"
                cart_activity_VAT_text_view.text="VAT: ${VAT.toString()}"
                cart_activity_total_text_view.text="TOTAL: ${TOTAL.toString()}"
                cart_activity_recycler_view.adapter=adapter
                adapter.setOnItemClickListener { item, view ->
                    var intent=Intent(view.context,CartShowProductActivity::class.java)
                    var intenty=item as CartAdapter
                    intent.putExtra("tesla",intenty.item)
                    startActivity(intent)
                }

            }

        })
    }

    override fun onResume() {
        getCart()
        super.onResume()
    }

    override fun onRestart() {
        getCart()
        super.onRestart()
    }
}