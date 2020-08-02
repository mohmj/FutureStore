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

//        var productName= intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.name
//        var productPrice=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.price.toString()
//        var productDescription=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.description.toString()
//        var productImageLink=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.imageLink.toString()

        show_product_activity_name_text_view.text= intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.name.toString()
        show_product_activity_price_text_view.text=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.price.toString()
        Picasso.get().load(intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.imageLink.toString()).into(show_product_activity_image_view)
        show_product_activity_description_text_view.text=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.description.toString()

    }
}