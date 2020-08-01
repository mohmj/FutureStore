package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.product_item.view.*

class ProductActivity : AppCompatActivity() {

    var adapter=GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        product_activity_recycler_view.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        adapter.add(TestProudct("Core i9 9900K",2346.0,"https://c1.neweggimages.com/NeweggImage/ProductImage/19-117-957-V01.jpg"))
        adapter.add(TestProudct("Core i9 10900K",3000.0,"https://cdn.mwave.com.au/images/400/intel_core_i9_10900k_10core_lga_1200_370ghz_unlocked_cpu_processor_ac34454_1.jpg"))
        adapter.add(TestProudct("Core i5 9600K",870.0,"https://c1.neweggimages.com/NeweggImage/ProductImage/19-117-959-V01.jpg"))
        adapter.add(TestProudct("Core i3 9100F",327.0,"https://c1.neweggimages.com/NeweggImage/ProductImage/19-118-072-V01.jpg"))






        product_activity_recycler_view.adapter=adapter
    }
}


class TestProudct(var name:String, var price:Double, var imageLink:String): Item<ViewHolder>(){

    override fun getLayout(): Int {
        return R.layout.product_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.product_item_name_text_view.text=name
        viewHolder.itemView.product_item_price_text_view.text=price.toString()
        Picasso.get().load(imageLink).into(viewHolder.itemView.product_item_image_view)
    }
}