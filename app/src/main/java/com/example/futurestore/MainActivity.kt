package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.futurestore.Services.Database
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

