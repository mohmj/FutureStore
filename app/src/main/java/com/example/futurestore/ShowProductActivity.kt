package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.Services.Database
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_product.*
import kotlinx.android.synthetic.main.product_item.*
import kotlinx.android.synthetic.main.product_item.view.*

class ShowProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)

        val productName= intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.name.toString()
        show_product_activity_name_text_view.text=productName

        val productPrice=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.price.toString()
        show_product_activity_price_text_view.text=productPrice

        val productDescription=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.description.toString()
        show_product_activity_description_text_view.text=productDescription

        val productImageLink=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.imageLink.toString()
        if(productImageLink != ""){
            Picasso.get().load(productImageLink).into(show_product_activity_image_view)
        }
    }
}