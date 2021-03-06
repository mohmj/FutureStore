package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_builder_show_product.*
class BuilderShowProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder_show_product)

        var uid=Firebase.auth.uid.toString()

        //CPU
        var CPU_product_number:String="";
        var CPU_name : String="";
        var CPU_category : String="";
        var  CPU_price : Double=0.0;
        var CPU_quantity : Int=0;
        var  CPU_image_link : String="";
        var CPU_description : String="";
        var  CPU_CPU_socket_type : String="";
        var  CPU_number_of_cores : Int=0;
        var  CPU_core_clock : Double=0.0;
        var  CPU_boost_clock : Double=0.0;
        var CPU_TDP : Int=0;
        var CPU_integrated_graphics_card : String="";

        //Motherboard
        var motherboard_product_number=""
        var motherboard_name : String="";
        var motherboard_category : String="";
        var motherboard_price : Double=0.0;
        var motherboard_quantity : Int=0;
        var motherboard_image_link : String="";
        var motherboard_description : String="";
        var motherboard_CPU_socket_type : String="";
        var motherboard_form_factor="";
        var motherboard_memory_frequency_max=0
        var motherboard_memory_max=0
        var motherboard_memory_number_of_slots=0
        var motherboard_memory_pins_slots=0
        var motherboard_chipset=""

        //GPU
        var GPU_product_number=""
        var GPU_name : String="";
        var GPU_category : String="";
        var GPU_price : Double=0.0;
        var GPU_quantity : Int=0;
        var GPU_image_link : String="";
        var GPU_description : String="";

        //RAM
        var RAM_product_number=""
        var RAM_name : String="";
        var RAM_category : String="";
        var RAM_price : Double=0.0;
        var RAM_quantity : Int=0;
        var RAM_image_link : String="";
        var RAM_description : String="";
        var RAM_type_of_RAM="";
        var RAM_pins=0;
        var RAM_speed=0;
        var RAM_number_of_slice=0;
        var RAM_signle_giga=0;
        var RAM_total_giga=0;
        var RAM_CAS_latency=0;

        //Memory
        var memory_product_number=""
        var memory_name : String="";
        var memory_category : String="";
        var memory_price : Double=0.0;
        var memory_quantity : Int=0;
        var memory_image_link : String="";
        var memory_description : String="";

        //Cooler
        var cooler_product_number=""
        var cooler_name : String="";
        var cooler_category : String="";
        var cooler_price : Double=0.0;
        var cooler_quantity : Int=0;
        var cooler_image_link : String="";
        var cooler_description : String="";

        //Case
        var case_product_number=""
        var case_name : String="";
        var case_category : String="";
        var case_price : Double=0.0;
        var case_quantity : Int=0;
        var case_image_link : String="";
        var case_description : String="";
        var case_type="";
        var case_form_factor=""



        var adapter=GroupAdapter<ViewHolder>()

        var category=intent.getStringExtra("category")

        when(category){
            "computer/CPU"->{
                var productInformation=intent.getParcelableExtra<ProductInformation>("one")
                builder_show_product_activity_name_text_view.text=productInformation?.name
                builder_show_product_activity_price_text_view.text=productInformation?.price.toString()
                builder_show_product_activity_description_text_view.text=productInformation?.description
                var productNumber=productInformation?.productNumber.toString()
                Picasso.get().load(productInformation?.imageLink).into(builder_show_product_activity_image_view)


                Firebase.database.getReference("products/$category").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisCPU=it.getValue(ComputerCPU::class.java)
                            if(thisCPU != null){
                                if(thisCPU.productNumber==productNumber){
                                    CPU_product_number=thisCPU.productNumber
                                    CPU_name=thisCPU.name
                                    CPU_category=thisCPU.category
                                    CPU_price=thisCPU.price
                                    CPU_quantity=thisCPU.quantity
                                    CPU_image_link=thisCPU.imageLink
                                    CPU_description=thisCPU.description
                                    CPU_CPU_socket_type=thisCPU.CPUSocketType
                                    CPU_number_of_cores=thisCPU.numberOfCores
                                    CPU_core_clock=thisCPU.coreClock
                                    CPU_boost_clock=thisCPU.BoostClock
                                    CPU_TDP=thisCPU.TDP
                                    CPU_integrated_graphics_card=thisCPU.integratedGraphicsCard
                                }
                            }
                        }
                    }
                })

                builder_show_product_activity_add_to_builder_button.setOnClickListener(){
                    Firebase.database.getReference("users/$uid/builder/CPU").setValue(ComputerCPU(
                        CPU_product_number,
                        CPU_name,
                        CPU_category,
                        CPU_price,
                        1,
                        CPU_image_link,
                        CPU_description,
                        CPU_CPU_socket_type,
                        CPU_number_of_cores,
                        CPU_core_clock,
                        CPU_boost_clock,
                        CPU_TDP,
                        CPU_integrated_graphics_card
                    )).addOnSuccessListener {
                        Firebase.database.getReference("users/$uid/builder/motherboard").removeValue()
                        Firebase.database.getReference("users/$uid/builder/GPU").removeValue()
                        Firebase.database.getReference("users/$uid/builder/RAM").removeValue()
                        Firebase.database.getReference("users/$uid/builder/memory").removeValue()
                        Firebase.database.getReference("users/$uid/builder/cooler").removeValue()
                        Firebase.database.getReference("users/$uid/builder/case").removeValue()
                        var intent=Intent(this,BuilderActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }

            "computer/motherboard"->{
                var productInformation=intent.getParcelableExtra<ComputerMotherboard>("one")
                builder_show_product_activity_name_text_view.text=productInformation?.name
                builder_show_product_activity_price_text_view.text=productInformation?.price.toString()
                builder_show_product_activity_description_text_view.text=productInformation?.description
                var productNumber=productInformation?.productNumber.toString()
                Picasso.get().load(productInformation?.imageLink).into(builder_show_product_activity_image_view)

                Firebase.database.getReference("products/computer/motherboard").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisMotherboard=it.getValue(ComputerMotherboard::class.java)
                            if(thisMotherboard != null){
                                if(thisMotherboard.productNumber==productNumber){
                                     motherboard_product_number=thisMotherboard.productNumber
                                     motherboard_name=thisMotherboard.name
                                     motherboard_category=thisMotherboard.category
                                     motherboard_price=thisMotherboard.price
                                     motherboard_quantity=thisMotherboard.quantity
                                     motherboard_image_link=thisMotherboard.imageLink
                                     motherboard_description=thisMotherboard.description
                                     motherboard_CPU_socket_type=thisMotherboard.CPUSocketType
                                     motherboard_form_factor=thisMotherboard.formFactor
                                     motherboard_memory_frequency_max=thisMotherboard.memoryFrequencyMax
                                     motherboard_memory_max=thisMotherboard.memoryMax
                                     motherboard_memory_number_of_slots=thisMotherboard.memoryNumberOfSlots
                                     motherboard_memory_pins_slots=thisMotherboard.memoryPinsOfSlots
                                     motherboard_chipset=thisMotherboard.chipset
                                }
                            }
                        }
                    }

                })

                builder_show_product_activity_add_to_builder_button.setOnClickListener(){
                    Firebase.database.getReference("users/$uid/builder/motherboard").setValue(ComputerMotherboard(
                                motherboard_product_number,
                                motherboard_name,
                                motherboard_category,
                                motherboard_price,
                                1,
                                motherboard_image_link,
                                motherboard_description,
                                motherboard_CPU_socket_type,
                                motherboard_form_factor,
                                motherboard_memory_frequency_max,
                                motherboard_memory_max,
                                motherboard_memory_number_of_slots,
                                motherboard_memory_pins_slots,
                                motherboard_chipset
                    )).addOnSuccessListener {
                        Firebase.database.getReference("users/$uid/builder/GPU").removeValue()
                        Firebase.database.getReference("users/$uid/builder/RAM").removeValue()
                        Firebase.database.getReference("users/$uid/builder/memory").removeValue()
                        Firebase.database.getReference("users/$uid/builder/cooler").removeValue()
                        Firebase.database.getReference("users/$uid/builder/case").removeValue()
                        var intent=Intent(this,BuilderActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }

            "computer/GPU"->{
                var productInformation=intent.getParcelableExtra<ProductInformation>("one")
                builder_show_product_activity_name_text_view.text=productInformation?.name
                builder_show_product_activity_price_text_view.text=productInformation?.price.toString()
                builder_show_product_activity_description_text_view.text=productInformation?.description
                var productNumber=productInformation?.productNumber.toString()
                Picasso.get().load(productInformation?.imageLink).into(builder_show_product_activity_image_view)

                Firebase.database.getReference("products/computer/GPU").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisGPU=it.getValue(ProductInformation::class.java)
                            if(thisGPU != null){
                                if(thisGPU.productNumber==productNumber){
                                    GPU_product_number= thisGPU.productNumber
                                    GPU_name= thisGPU.name
                                    GPU_category= thisGPU.category
                                    GPU_price= thisGPU.price
                                    GPU_quantity= thisGPU.quantity
                                    GPU_image_link= thisGPU.imageLink
                                    GPU_description= thisGPU.description
                                }
                            }
                        }
                    }

                })


                builder_show_product_activity_add_to_builder_button.setOnClickListener(){
                    Firebase.database.getReference("users/$uid/builder/GPU").setValue(ProductInformation(
                        GPU_product_number,
                        GPU_name,
                        GPU_category,
                        GPU_price,
                        1,
                        GPU_image_link,
                        GPU_description
                    )).addOnSuccessListener {
                        var intent=Intent(this,BuilderActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                     }
                }
            }

            "computer/RAM"->{
                var productInformation=intent.getParcelableExtra<ComputerRAM>("one")
                builder_show_product_activity_name_text_view.text=productInformation?.name
                builder_show_product_activity_price_text_view.text=productInformation?.price.toString()
                builder_show_product_activity_description_text_view.text=productInformation?.description
                var productNumber=productInformation?.productNumber.toString()
                Picasso.get().load(productInformation?.imageLink).into(builder_show_product_activity_image_view)

                Firebase.database.getReference("products/computer/RAM").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisRAM=it.getValue(ComputerRAM::class.java)
                            if(thisRAM != null){
                                if(thisRAM.productNumber==productNumber){
                                     RAM_product_number=thisRAM.productNumber
                                     RAM_name=thisRAM.name
                                     RAM_category=thisRAM.category
                                     RAM_price=thisRAM.price
                                     RAM_quantity=thisRAM.quantity
                                     RAM_image_link=thisRAM.imageLink
                                     RAM_description=thisRAM.description
                                     RAM_type_of_RAM=thisRAM.typeOfRam
                                     RAM_pins=thisRAM.pins
                                     RAM_speed=thisRAM.speed
                                     RAM_number_of_slice=thisRAM.numberOfSlice
                                     RAM_signle_giga=thisRAM.singleGiga
                                     RAM_total_giga=thisRAM.totalGiga
                                     RAM_CAS_latency=thisRAM.CASLatency
                                }
                            }
                        }
                    }

                })

                builder_show_product_activity_add_to_builder_button.setOnClickListener(){
                    Firebase.database.getReference("users/$uid/builder/RAM").setValue(ComputerRAM(
                                RAM_product_number,
                                RAM_name,
                                RAM_category,
                                RAM_price,
                                1,
                                RAM_image_link,
                                RAM_description,
                                RAM_type_of_RAM,
                                RAM_pins,
                                RAM_speed,
                                RAM_number_of_slice,
                                RAM_signle_giga,
                                RAM_total_giga,
                                RAM_CAS_latency
                    )).addOnSuccessListener {
                        var intent=Intent(this,BuilderActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }

            "computer/memory"->{
                var productInformation=intent.getParcelableExtra<ProductInformation>("one")
                builder_show_product_activity_name_text_view.text=productInformation?.name
                builder_show_product_activity_price_text_view.text=productInformation?.price.toString()
                builder_show_product_activity_description_text_view.text=productInformation?.description
                var productNumber=productInformation?.productNumber.toString()
                Picasso.get().load(productInformation?.imageLink).into(builder_show_product_activity_image_view)

                Firebase.database.getReference("products/computer/memory").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisMemory=it.getValue(ProductInformation::class.java)
                            if(thisMemory != null){
                                if(thisMemory.productNumber==productNumber){
                                    memory_product_number= thisMemory.productNumber
                                    memory_name= thisMemory.name
                                    memory_category= thisMemory.category
                                    memory_price= thisMemory.price
                                    memory_quantity= thisMemory.quantity
                                    memory_image_link= thisMemory.imageLink
                                    memory_description= thisMemory.description
                                }
                            }
                        }
                    }

                })


                builder_show_product_activity_add_to_builder_button.setOnClickListener(){
                    Firebase.database.getReference("users/$uid/builder/memory").setValue(ProductInformation(
                        memory_product_number,
                        memory_name,
                        memory_category,
                        memory_price,
                        1,
                        memory_image_link,
                        memory_description
                    )).addOnSuccessListener {
                        var intent=Intent(this,BuilderActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }

            "computer/cooler"->{
                var productInformation=intent.getParcelableExtra<ProductInformation>("one")
                builder_show_product_activity_name_text_view.text=productInformation?.name
                builder_show_product_activity_price_text_view.text=productInformation?.price.toString()
                builder_show_product_activity_description_text_view.text=productInformation?.description
                var productNumber=productInformation?.productNumber.toString()
                Picasso.get().load(productInformation?.imageLink).into(builder_show_product_activity_image_view)

                Firebase.database.getReference("products/computer/cooler").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisCooler=it.getValue(ProductInformation::class.java)
                            if(thisCooler != null){
                                if(thisCooler.productNumber==productNumber){
                                    cooler_product_number= thisCooler.productNumber
                                    cooler_name= thisCooler.name
                                    cooler_category= thisCooler.category
                                    cooler_price= thisCooler.price
                                    cooler_quantity= thisCooler.quantity
                                    cooler_image_link= thisCooler.imageLink
                                    cooler_description= thisCooler.description
                                }
                            }
                        }
                    }

                })

                builder_show_product_activity_add_to_builder_button.setOnClickListener(){
                    Firebase.database.getReference("users/$uid/builder/cooler").setValue(ProductInformation(
                        cooler_product_number,
                        cooler_name,
                        cooler_category,
                        cooler_price,
                        1,
                        cooler_image_link,
                        cooler_description
                    )).addOnSuccessListener {
                        var intent=Intent(this,BuilderActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }

            "computer/case"->{
                var productInformation=intent.getParcelableExtra<ComputerCase>("one")
                builder_show_product_activity_name_text_view.text=productInformation?.name
                builder_show_product_activity_price_text_view.text=productInformation?.price.toString()
                builder_show_product_activity_description_text_view.text=productInformation?.description
                var productNumber=productInformation?.productNumber.toString()
                Picasso.get().load(productInformation?.imageLink).into(builder_show_product_activity_image_view)

                Firebase.database.getReference("products/computer/case").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            var thisCase=it.getValue(ComputerCase::class.java)
                            if(thisCase!=null){
                                if(thisCase.productNumber==productNumber){
                                    case_product_number=thisCase.productNumber
                                    case_name=thisCase.name
                                    case_category=thisCase.category
                                    case_price=thisCase.price
                                    case_quantity=thisCase.quantity
                                    case_description=thisCase.description
                                    case_image_link=thisCase.imageLink
                                    case_form_factor=thisCase.formFactor
                                    case_type=thisCase.type
                                }
                            }
                        }
                    }

                })

                builder_show_product_activity_add_to_builder_button.setOnClickListener(){
                    Firebase.database.getReference("users/$uid/builder/case").setValue(ComputerCase(
                        case_product_number,
                        case_name,
                        case_category,
                        case_price,
                        1,
                        case_image_link,
                        case_description,
                        case_type,
                        case_form_factor
                    )).addOnSuccessListener {
                        var intent=Intent(this,BuilderActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}