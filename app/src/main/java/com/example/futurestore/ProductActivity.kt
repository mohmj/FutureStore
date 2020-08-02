package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Services.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.product_item.view.*

class ProductActivity : AppCompatActivity() {

//    var adapter=GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        var category=intent.getStringExtra(Database().category)
        supportActionBar?.title=category

        product_activity_recycler_view.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)




        var reference=Firebase.database.getReference("products/$category")

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var adapter=GroupAdapter<ViewHolder>()
                snapshot.children.forEach(){
                    var pro=it.getValue(ProductInformation::class.java)
                    if(pro != null){
                        adapter.add(TestProudct(pro))
                    }
                }
                product_activity_recycler_view.adapter=adapter
            }

        })





    }
}

@Parcelize
class ProductInformation(var name:String, var price:String, var imageLink:String):Parcelable{
    constructor():this("","","")
}

class TestProudct(var info:ProductInformation): Item<ViewHolder>(){

    override fun getLayout(): Int {
        return R.layout.product_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.product_item_name_text_view.text=info.name
        viewHolder.itemView.product_item_price_text_view.text=info.price
        Picasso.get().load(info.imageLink).into(viewHolder.itemView.product_item_image_view)
    }
}