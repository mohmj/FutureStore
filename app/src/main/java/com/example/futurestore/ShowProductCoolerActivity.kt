package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.ComputerAdapters.CoolerAdapter
import com.example.futurestore.Models.Computer.ComputerCooler
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
import kotlinx.android.synthetic.main.activity_show_product.*
import kotlinx.android.synthetic.main.activity_show_product_cooler.*

class ShowProductCoolerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product_cooler)

        var uid=Firebase.auth.uid

        var productNumber=intent.getParcelableExtra<ComputerCooler>("cooler_put")?.productNumber.toString()

        val productName =
            intent.getParcelableExtra<ComputerCooler>("cooler_put")?.name.toString()
        show_product_activity_name_text_view_cooler.text = productName

        val productCategory =
            intent.getParcelableExtra<ComputerCooler>("cooler_put")?.category.toString()

        val productPrice =
            intent.getParcelableExtra<ComputerCooler>("cooler_put")?.price.toString()
        show_product_activity_price_text_view_cooler.text = productPrice

        val productDescription =
            intent.getParcelableExtra<ComputerCooler>("cooler_put")?.description.toString()
        show_product_activity_description_text_view_cooler.text = productDescription

        val productImageLink =
            intent.getParcelableExtra<ComputerCooler>("cooler_put")?.imageLink.toString()
        if (productImageLink != "") {
            Picasso.get().load(productImageLink).into(show_product_activity_image_view_cooler)
        }

        show_product_activity_cart_button_cooler.setOnClickListener() {
            if (uid == null) {
                Toast.makeText(this, "You must login to can add item in cart", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Firebase.database.getReference("users/$uid/cart").child(productName).setValue(
                    ProductInformation(
                        productNumber,
                        productName,
                        productCategory,
                        productPrice.toDouble(),
                        1,
                        productImageLink,
                        productDescription
                    )
                ).addOnSuccessListener {
                    Toast.makeText(this, "The item is added.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        show_product_activity_wish_list_button_cooler.setOnClickListener() {
            if (uid == null) {
                Toast.makeText(
                    this,
                    "You must login to can add item in wish list",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Firebase.database.getReference("users/$uid/wish_list").child(productName).setValue(
                    ProductInformation(
                        productNumber,
                        productName,
                        productCategory,
                        productPrice.toDouble(),
                        1,
                        productImageLink,
                        productDescription
                    )
                ).addOnSuccessListener {
                    Toast.makeText(this, "The item is added.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}