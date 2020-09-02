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
import java.math.RoundingMode

class CartActivity : AppCompatActivity() {

    var SubTotal:Double=0.0
    var Total:Double=0.0
    var VAT:Double=0.0
    var quantity:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        getCart()

        cart_activity_button.setOnClickListener(){
            var intent=Intent(this,CartChooseAddressActivity::class.java)
            intent.putExtra(Database().cartTotal,Total.toString())
            startActivity(intent)
        }

    }


    fun getCart(){
        SubTotal=0.0
        Total=0.0
        VAT=0.0
        quantity=0


        var uid=Firebase.auth.uid
        var adapter=GroupAdapter<ViewHolder>()
        cart_activity_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        if(uid==null){
            startActivity(Intent(this,SigninActivity::class.java))
        }else{

//            Firebase.database.getReference("users/$uid/cart").addChildEventListener(object:ChildEventListener{
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                    var thisProduct=snapshot.getValue(ProductInformation::class.java)
//                    if(thisProduct != null){
//                        adapter.add(CartAdapter(thisProduct))
//                        Total+=(thisProduct.price*thisProduct.quantity)
//                        quantity+=thisProduct.quantity
//                    }
//                    cart_activity_recycler_view.adapter=adapter
//                    cart_activity_sub_total_text_view.text="Subtotal: ${(Total*0.85).toString()}"
//                    cart_activity_VAT_text_view.text="VAT: ${(Total*0.15).toString()}"
//                    cart_activity_total_text_view.text="Total: ${Total.toString()}"
//                    cart_activity_quantity_text_view.text="Quantity: $quantity"
//                    adapter.setOnItemClickListener { item, view ->
//                        var intent=Intent(view.context,CartShowProductActivity::class.java)
//                        var intenty=item as CartAdapter
//                        intent.putExtra("tesla",intenty.item)
//                        startActivity(intent)
//                    }
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    var thisProduct=snapshot.getValue(ProductInformation::class.java)
//                    if(thisProduct != null){
//                        adapter.add(CartAdapter(thisProduct))
//                        Total+=(thisProduct.price*thisProduct.quantity)
//                        quantity+=thisProduct.quantity
//                    }
//                    cart_activity_recycler_view.adapter=adapter
//                    cart_activity_sub_total_text_view.text="Subtotal: ${(Total*0.85).toString()}"
//                    cart_activity_VAT_text_view.text="VAT: ${(Total*0.15).toString()}"
//                    cart_activity_total_text_view.text="Total: ${Total.toString()}"
//                    cart_activity_quantity_text_view.text="Quantity: $quantity"
//                    adapter.setOnItemClickListener { item, view ->
//                        var intent=Intent(view.context,CartShowProductActivity::class.java)
//                        var intenty=item as CartAdapter
//                        intent.putExtra("tesla",intenty.item)
//                        startActivity(intent)
//                    }
//                }
//
//                override fun onChildRemoved(snapshot: DataSnapshot) {
//                    TODO("Not yet implemented")
//                }
//
//            })

            Firebase.database.getReference("users/$uid/cart").addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        var thisProduct=it.getValue(ProductInformation::class.java)
                        if(thisProduct != null){
                            adapter.add(CartAdapter(thisProduct))
                            Total+=(thisProduct.price*thisProduct.quantity)
                            quantity+=thisProduct.quantity
                        }
                    }
                    cart_activity_recycler_view.adapter=adapter
                    cart_activity_sub_total_text_view.text="Subtotal: ${(Total*0.85).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()}"
                    cart_activity_VAT_text_view.text="VAT: ${(Total*0.15).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()}"
                    cart_activity_total_text_view.text="Total: ${Total.toString()}"
                    cart_activity_quantity_text_view.text="Quantity: $quantity"
                    adapter.setOnItemClickListener { item, view ->
                        var intent=Intent(view.context,CartShowProductActivity::class.java)
                        var intenty=item as CartAdapter
                        intent.putExtra("tesla",intenty.item)
                        startActivity(intent)
                    }
                }
            })
        }
    }

//    override fun onRestart() {
//        getCart()
//        super.onRestart()
//    }

//    override fun onResume() {
//        getCart()
//        super.onResume()
//    }


}

