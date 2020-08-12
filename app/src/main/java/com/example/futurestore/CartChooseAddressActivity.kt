package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.ShowLocationAdapter
import com.example.futurestore.Models.AddressInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_cart_choose_address.*

class CartChooseAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_choose_address)

        var uid = Firebase.auth.uid
        var adapter = GroupAdapter<ViewHolder>()
        cart_choose_address_activity.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        var oneTotal = intent.getStringExtra(Database().cartTotal)
        Log.d("tasty", oneTotal.toString())

        var reference = Firebase.database.getReference("users/${uid.toString()}/addresses")
//        reference.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                snapshot.children.forEach {
//                    val location = snapshot.getValue(Location::class.java)
//                    if (location != null) {
//                        adapter.add(ShowLocationAdapter(location))
//                    }
//                }
//                cart_choose_address_activity.adapter = adapter
//                adapter.setOnItemClickListener { item, view ->
//                    var intent = Intent(view.context, CartSummaryActivity::class.java)
//                    var intenty = item as ShowLocationAdapter
//                    intent.putExtra(Database().cartLocation, intenty.location)
//                    intent.putExtra(
//                        Database().cartTotal,
//                        intent.getStringExtra(Database().cartTotal).toString()
//                    )
//                    startActivity(intent)
//                }
//
//            }
//
//
//        })

        reference.addChildEventListener(object:ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val location=snapshot.getValue(AddressInformation::class.java)
                if(location != null){
                    adapter.add(ShowLocationAdapter(location))
                }
                cart_choose_address_activity.adapter=adapter
                adapter.setOnItemClickListener { item, view ->
                    var intent= Intent(view.context,CartSummaryActivity::class.java)
                    var intenty=item as ShowLocationAdapter
                    intent.putExtra(Database().cartLocation,intenty.location)
                    intent.putExtra(Database().cartTotal,oneTotal.toString())
                    startActivity(intent)
                }
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val location=snapshot.getValue(AddressInformation::class.java)
                if(location != null){
                    adapter.add(ShowLocationAdapter(location))
                }
                cart_choose_address_activity.adapter=adapter
                adapter.setOnItemClickListener { item, view ->
                    var intent= Intent(view.context,CartSummaryActivity::class.java)
                    var intenty=item as ShowLocationAdapter
                    intent.putExtra(Database().cartLocation,intenty.location)
                    intent.putExtra(Database().cartTotal,oneTotal.toString())
                    startActivity(intent)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.addresses_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId
        when(id){
            R.id.addresses_menu_add_location ->{
                startActivity(Intent(this,AddressNewActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}