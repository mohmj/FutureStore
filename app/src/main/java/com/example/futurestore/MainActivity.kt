package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.TestProudct
import com.example.futurestore.Models.ProductInformation
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.desktop_item.view.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Desktop
        main_activity_desktop_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        var desktopAdapter=GroupAdapter<ViewHolder>()
        var desktopReference=Firebase.database.getReference("products/computer/desktop")
        desktopReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach(){
                    var desktop=it.getValue(ProductInformation::class.java)
                    if(desktop != null){
                        desktopAdapter.add(DesktopTest(desktop))
                    }
                }
                main_activity_desktop_recycler_view.adapter=desktopAdapter
                desktopAdapter.setOnItemClickListener(){item, view ->
                    var intent= Intent(view.context,ShowProductActivity::class.java)
                    var producty=item as DesktopTest
                    intent.putExtra(Database().productInformation,producty.product)
                    startActivity(intent)
                }
            }

        })


        // Special offers
        main_activity_special_offers_recycler_view.layoutManager=StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        var specialOffersAdapter=GroupAdapter<ViewHolder>()
        var specialOffersReference=Firebase.database.getReference("products/special_offers")
        specialOffersReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach(){
                    var offer=it.getValue(ProductInformation::class.java)
                    if(offer != null){
                        specialOffersAdapter.add(DesktopTest(offer))
                    }
                }
                main_activity_special_offers_recycler_view.adapter=specialOffersAdapter
                specialOffersAdapter.setOnItemClickListener(){item, view ->
                    var intent= Intent(view.context,ShowProductActivity::class.java)
                    var producty=item as DesktopTest
                    intent.putExtra(Database().productInformation,producty.product)
                    startActivity(intent)
                }
            }

        })


        main_activity_menu_image_button.setOnClickListener(){
            startActivity(Intent(this,MenuActivity::class.java))
        }

        main_activity_cpu_categories.setOnClickListener(){
            category("computer/CPU")
        }
        main_activity_gpu_categories.setOnClickListener(){
            category("computer/GPU")
        }
        main_activity_motherboard_categories.setOnClickListener(){
            category("computer/motherboard")
        }
        main_activity_ram_categories.setOnClickListener(){
            category("computer/ram")
        }
        main_activity_hardisks_categories.setOnClickListener(){
            category("computer/hardisk")
        }
        main_activity_water_cooler_categories.setOnClickListener(){
            category("computer/waterCooler")
        }
        main_activity_air_cooler_categories.setOnClickListener(){
            category("computer/airCooler")
        }
        main_activity_case_categories.setOnClickListener(){
            category("computer/case")
        }
        main_activity_headphone_categories.setOnClickListener(){
            category("computer/headphone")
        }
        main_activity_sound_card_categories.setOnClickListener(){
            category("computer/soundCard")
        }




    }

    fun category(category:String){
        var intent=Intent(this,ProductActivity::class.java)
       intent.putExtra(Database().category,category)
        startActivity(intent)
    }


}

class DesktopTest(var product:ProductInformation):Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.desktop_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Picasso.get().load(product.imageLink).into(viewHolder.itemView.desktop_item_image_view)
    }

}