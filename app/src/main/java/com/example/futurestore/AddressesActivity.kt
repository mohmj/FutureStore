package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.ShowLocationAdapter
import com.example.futurestore.Models.Location
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_addresses.*

class AddressesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)

        addresses_acitivity_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        getLocations()






    }

//    override fun onResume() {
//        getLocations()
//        super.onResume()
//    }

    override fun onRestart() {
        getLocations()
        super.onRestart()
    }

    fun getLocations(){
        var adapter=GroupAdapter<ViewHolder>()
        var uid=Firebase.auth.uid
        var reference=Firebase.database.getReference("users/${uid.toString()}/addresses")
        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var location=it.getValue(Location::class.java)
                    if(location != null){
                        adapter.add(ShowLocationAdapter(location))
                    }
                }
                addresses_acitivity_recycler_view.adapter=adapter
                adapter.setOnItemClickListener { item, view ->
                    val intent=Intent(view.context, UpdateAddressActivity::class.java)
                    val intenty=item as ShowLocationAdapter
                    intent.putExtra(Database().updateLocation,intenty.location)
                    startActivity(intent)
                }
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
