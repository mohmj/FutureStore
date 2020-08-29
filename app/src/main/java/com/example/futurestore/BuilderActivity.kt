package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.futurestore.Models.Computer.ComputerCPU
import com.example.futurestore.Models.Computer.ComputerCase
import com.example.futurestore.Models.Computer.ComputerMotherboard
import com.example.futurestore.Models.Computer.ComputerRAM
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_builder.*

class BuilderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder)

        var uid=Firebase.auth.uid.toString()

        var CPUSocketType=""

        //Get CPU
        Firebase.database.getReference("users/$uid/builder").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var thisCPU=it.getValue(ComputerCPU::class.java)
                    if(thisCPU != null){
                        if(thisCPU.category=="computer/CPU"){
                            builder_activity_CPU_name_text_view.text=thisCPU.name
                            builder_activity_CPU_socket_type_text_view.text="Socket type: ${thisCPU.CPUSocketType}"
                            CPUSocketType=thisCPU.CPUSocketType
                            builder_activity_CPU_number_of_cores_text_view.text="Number of cores: ${thisCPU.numberOfCores.toString()}"
                            builder_activity_CPU_core_clock_text_view.text="Core clock: ${thisCPU.coreClock.toString()} GH.z"
                            builder_activity_CPU_boost_clock_text_view.text="Boost clock: ${thisCPU.BoostClock.toString()} GH.z"
                            builder_activity_CPU_price_text_view.text="Price\n${thisCPU.price.toString()} "
                            Picasso.get().load(thisCPU.imageLink).into(builder_activity_CPU_image_view)
                        }
                    }
                }
            }

        })

        //Get motherboard
        Firebase.database.getReference("users/$uid/builder").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var thisMotherboard=it.getValue(ComputerMotherboard::class.java)
                    if(thisMotherboard != null){
                        if(thisMotherboard.category=="computer/motherboard"){
                            builder_activity_motherboard_name_text_view.text=thisMotherboard.name
                            builder_activity_motherboard_socket_type_text_view.text="Socket type: ${thisMotherboard.CPUSocketType}"
                            builder_activity_motherboard_chipset_text_view.text="Chipset: ${thisMotherboard.chipset}"
                            builder_activity_motherboard_form_factor_text_view.text="Form factor: ${thisMotherboard.formFactor}"
                            builder_activity_motherboard_memory_frequency_max_text_view.text="Memory frequency max: ${thisMotherboard.memoryFrequencyMax}"
                            builder_activity_motherboard_memory_max_text_view.text="Memory max: ${thisMotherboard.memoryMax}"
                            builder_activity_motherboard_memory_number_of_slots_text_view.text="Number of slots: ${thisMotherboard.memoryNumberOfSlots}"
                            builder_activity_motherboard_memory_pins_of_slots_text_view.text="Memory pins: ${thisMotherboard.memoryPinsOfSlots}"
                            builder_activity_motherboard_price_text_view.text="Price\n${thisMotherboard.price.toString()} "
                            Picasso.get().load(thisMotherboard.imageLink).into(builder_activity_motherboard_image_view)
                        }
                    }
                }
            }
        })

        //Get RAM
        Firebase.database.getReference("users/$uid/builder").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var thisRAM=it.getValue(ComputerRAM::class.java)
                    if(thisRAM != null){
                        if(thisRAM.category=="computer/RAM"){
                            Picasso.get().load(thisRAM.imageLink).into(builder_activity_RAM_image_view)
                            builder_activity_RAM_name_text_view.text=thisRAM.name
                            builder_activity_RAM_total_giga_text_view.text="${thisRAM.totalGiga.toString()} GB"
                            builder_activity_RAM_type_of_RAM_text_view.text="Type: ${thisRAM.typeOfRam}"
                            builder_activity_RAM_speed_text_view.text="Speed: ${thisRAM.speed.toString()} GH.z"
                            builder_activity_RAM_pins_text_view.text="${thisRAM.pins.toString()} pins"
                            builder_activity_RAM_number_of_slice_text_view.text="${thisRAM.numberOfSlice.toString()} pices"
                            builder_activity_RAM_CAS_latency_text_view.text="CAS latency: ${thisRAM.CASLatency.toString()}"
                            builder_activity_RAM_price_text_view.text="Price\n${thisRAM.price.toString()}"
                        }
                    }
                }
            }
        })

        //Get case
        Firebase.database.getReference("users/$uid/builder").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var thisCase=it.getValue(ComputerCase::class.java)
                    if(thisCase != null){
                        if(thisCase.category=="computer/case"){
                            Picasso.get().load(thisCase.imageLink).into(builder_activity_case_image_view)
                            builder_activity_case_name_text_view.text=thisCase.name
                            builder_activity_case_form_factor_text_view.text="Form factor: ${thisCase.formFactor}"
                            builder_activity_case_type_text_view.text="Type: ${thisCase.type}"
                            builder_activity_case_price_text_view.text="Price\n${thisCase.price.toString()}"
                        }
                    }
                }
            }
        })

        builder_activity_CPU_button.setOnClickListener(){

                var intent= Intent(this,BuilderProductsActivity::class.java)
                intent.putExtra(Database().builder_item,"CPU")
                intent.putExtra(Database().builder_CPU_socket_type,CPUSocketType)
                startActivity(intent)


        }

        builder_activity_motherboard_button.setOnClickListener(){
            if(CPUSocketType==""){
                Toast.makeText(this,"You must choose a processor before the motherboard. ",Toast.LENGTH_SHORT).show()
            }else {
                var intent = Intent(this, BuilderProductsActivity::class.java)
                intent.putExtra(Database().builder_item, "motherboard")
                startActivity(intent)
            }
        }

        builder_activity_RAM_button.setOnClickListener(){
            var intent= Intent(this,BuilderProductsActivity::class.java)
            intent.putExtra(Database().builder_item,"RAM")
            startActivity(intent)
        }

        builder_activity_case_button.setOnClickListener(){
            var intent= Intent(this,BuilderProductsActivity::class.java)
            intent.putExtra(Database().builder_item,"case")
            startActivity(intent)
        }
    }
}