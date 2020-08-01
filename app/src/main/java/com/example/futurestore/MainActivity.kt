package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.view.menu.MenuView
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.categories.view.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_activity_menu_image_button.setOnClickListener(){
            startActivity(Intent(this,MenuActivity::class.java))
        }

        main_activity_cpu_categories.setOnClickListener(){
            categoryCPU()
        }
        main_activity_cpu_image_view.setOnClickListener(){
            categoryCPU()
        }
        main_activity_cpu_name_text_view.setOnClickListener(){
            categoryCPU()
        }


    }

    fun categoryCPU(){
        startActivity(Intent(this,ProductActivity::class.java))
    }
}

