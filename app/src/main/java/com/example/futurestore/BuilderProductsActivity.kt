package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.ComputerAdapters.CaseAdapter
import com.example.futurestore.Adapters.ComputerAdapters.MotherboardAdapter
import com.example.futurestore.Adapters.ComputerAdapters.RamAdapter
import com.example.futurestore.Adapters.ProductAdapter
import com.example.futurestore.Models.Computer.ComputerCPU
import com.example.futurestore.Models.Computer.ComputerCase
import com.example.futurestore.Models.Computer.ComputerMotherboard
import com.example.futurestore.Models.Computer.ComputerRAM
import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_builder_products.*

class BuilderProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder_products)

        var uid=Firebase.auth.uid.toString()

        var adapter=GroupAdapter<ViewHolder>()
        builder_product_activity_recycler_view.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        var category=intent.getStringExtra(Database().builder_item).toString()
        Toast.makeText(this,category,Toast.LENGTH_SHORT).show()
        var CPUSocketType=""
        var memoryFrequencyMax=0
        var memoryMax=0
        var memoryNumberOfSlots=0
        var memoryPins=0
        var formFactor=""


        when(category){
            "CPU"->{
                Firebase.database.getReference("products/computer/$category").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var product=it.getValue(ProductInformation::class.java)
                            if(product != null){
                                adapter.add(ProductAdapter(product))
                            }
                        }
                        builder_product_activity_recycler_view.adapter=adapter
                        adapter.setOnItemClickListener { item, view ->
                            var intent=Intent(view.context,BuilderShowProductActivity::class.java)
                            var intity=item as ProductAdapter
                            intent.putExtra("category","computer/CPU")
                            intent.putExtra("one",intity.info)
                            startActivity(intent)
                        }
                    }
                })
            }

            "motherboard"->{
                Firebase.database.getReference("users/$uid/builder").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisCPU=it.getValue(ComputerCPU::class.java)
                            if(thisCPU != null){
                                if(thisCPU.category=="computer/CPU"){
                                    CPUSocketType=thisCPU.CPUSocketType
                                    Firebase.database.getReference("products/computer/$category").addListenerForSingleValueEvent(object:ValueEventListener{
                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            snapshot.children.forEach {
                                                var product=it.getValue(ComputerMotherboard::class.java)
                                                if(product != null){
                                                    if(product.CPUSocketType==CPUSocketType){
                                                        adapter.add(MotherboardAdapter(product))
                                                    }
                                                }
                                            }
                                            builder_product_activity_recycler_view.adapter=adapter
                                            adapter.setOnItemClickListener { item, view ->
                                                var intent=Intent(view.context,BuilderShowProductActivity::class.java)
                                                var intity=item as MotherboardAdapter
                                                intent.putExtra("category","computer/motherboard")
                                                intent.putExtra("one",intity.item)
                                                startActivity(intent)
                                            }
                                        }

                                    })
                                }
                            }
                        }
                    }

                })
            }
            "RAM"->{
                Firebase.database.getReference("users/$uid/builder").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisMotherboard=it.getValue(ComputerMotherboard::class.java)
                            if(thisMotherboard != null){
                                if(thisMotherboard.category=="computer/motherboard"){
                                    memoryFrequencyMax=thisMotherboard.memoryFrequencyMax
                                    memoryMax=thisMotherboard.memoryMax
                                    memoryNumberOfSlots=thisMotherboard.memoryNumberOfSlots
                                    memoryPins=thisMotherboard.memoryPinsOfSlots
                                    Firebase.database.getReference("products/computer/$category").addListenerForSingleValueEvent(object:ValueEventListener{
                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            snapshot.children.forEach {
                                                var thisRAM=it.getValue(ComputerRAM::class.java)
                                                if(thisRAM != null){
                                                    if(thisRAM.pins==memoryPins && thisRAM.speed<=memoryFrequencyMax && thisRAM.numberOfSlice<=memoryNumberOfSlots && thisRAM.totalGiga <= memoryMax){
                                                        adapter.add(RamAdapter(thisRAM))
                                                    }
                                                }
                                            }
                                            builder_product_activity_recycler_view.adapter=adapter
                                            adapter.setOnItemClickListener { item, view ->
                                                var intent=Intent(view.context,BuilderShowProductActivity::class.java)
                                                var intity=item as RamAdapter
                                                intent.putExtra("category","computer/RAM")
                                                intent.putExtra("one",intity.item)
                                                startActivity(intent)
                                            }
                                        }

                                    })
                                }
                            }
                        }
                    }

                })
            }
            "case"->{
                Firebase.database.getReference("users/$uid/builder").addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisMotherboard=it.getValue(ComputerMotherboard::class.java)
                            if(thisMotherboard != null){
                                if(thisMotherboard.category=="computer/motherboard"){
                                    formFactor=thisMotherboard.formFactor
                                    Firebase.database.getReference("products/computer/case").addListenerForSingleValueEvent(object:ValueEventListener{
                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            snapshot.children.forEach {
                                                var thisCase=it.getValue(ComputerCase::class.java)
                                                if(thisCase != null){
                                                    if(thisCase.formFactor==formFactor){
                                                        adapter.add(CaseAdapter(thisCase))
                                                    }
                                                }
                                            }
                                            builder_product_activity_recycler_view.adapter=adapter
                                            adapter.setOnItemClickListener { item, view ->
                                                var intent=Intent(view.context,ComputerCase::class.java)
                                                var intity=item as CaseAdapter
                                                intent.putExtra("category","computer/case")
                                                intent.putExtra("one",intity.item)
                                                startActivity(intent)
                                            }
                                        }

                                    })
                                }
                            }
                        }
                    }

                })
            }
        }
    }
}