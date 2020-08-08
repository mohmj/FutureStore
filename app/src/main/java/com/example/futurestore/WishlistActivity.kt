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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_wishlist.*

class WishlistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        checkLogin()
        wishlist_activity_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        getWishlist()



    }
    fun checkLogin(){
        var uid= Firebase.auth.uid
        if(uid == null){
            startActivity(Intent(this,SigninActivity::class.java))
        }
    }
    fun getWishlist(){
        var adapter= GroupAdapter<ViewHolder>()
        var uid=Firebase.auth.uid
        var cartReference=Firebase.database.getReference("users/$uid/wish_list")

        cartReference.addListenerForSingleValueEvent(object: ValueEventListener {
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
                wishlist_activity_recycler_view.adapter=adapter
                adapter.setOnItemClickListener { item, view ->
                    var intent=Intent(view.context,WishlistShowProductActivity::class.java)
                    var intenty=item as CartAdapter
                    intent.putExtra(Database().wishListIntent,intenty.item)
                    startActivity(intent)
                }

            }

        })
    }

    override fun onRestart() {
        checkLogin()
        getWishlist()
        super.onRestart()
    }

    override fun onResume() {
        checkLogin()
        getWishlist()
        super.onResume()
    }
}