package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_product.*
import kotlinx.android.synthetic.main.product_item.*
import kotlinx.android.synthetic.main.product_item.view.*

class ProductShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)
        var uid = Firebase.auth.uid

        var productNumber=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.productNumber.toString()

        val productName =
            intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.name.toString()
        show_product_activity_name_text_view.text = productName

        val productCategory =
            intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.category.toString()

        val productPrice =
            intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.price.toString()
        show_product_activity_price_text_view.text = productPrice

        val productDescription =
            intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.description.toString()
        show_product_activity_description_text_view.text = productDescription

        val productQuantity=intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.quantity

        var quantity=1

        val productImageLink =
            intent.getParcelableExtra<ProductInformation>(Database().productInformation)?.imageLink.toString()
        if (productImageLink != "") {
            Picasso.get().load(productImageLink).into(show_product_activity_image_view)
        }

        show_product_activity_quantity_add_button.setOnClickListener(){
            if(quantity < productQuantity!!){
                quantity++
                show_product_activity_quantity_text_view.text=quantity.toString()
            }else{
                Toast.makeText(this, "You can't add more than $quantity", Toast.LENGTH_SHORT).show()
            }
        }

        show_product_activity_quantity_minus_button.setOnClickListener(){
            quantity--
            if(quantity==0){
                quantity=1
            }else{
                show_product_activity_quantity_text_view.text=quantity.toString()
            }
        }


        show_product_activity_cart_button.setOnClickListener() {
            if (uid == null) {
                Toast.makeText(this, "You must login to can add item in cart", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if(productQuantity==0){
                    Toast.makeText(this,"The product is not available for now .", Toast.LENGTH_SHORT).show()
                }else {
                    Firebase.database.getReference("users/$uid/cart/$productNumber").setValue(
                        ProductInformation(
                            productNumber,
                            productName,
                            productCategory,
                            productPrice.toDouble(),
                            quantity,
                            productImageLink,
                            productDescription
                        )
                    ).addOnSuccessListener {
                        Toast.makeText(this, "The item is added.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



        show_product_activity_wish_list_button.setOnClickListener() {
            if (uid == null) {
                Toast.makeText(
                    this,
                    "You must login to can add item in wish list",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                    Firebase.database.getReference("users/$uid/wish_list").child(productNumber).setValue(
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


        fun checkLogin() {
            var uid = Firebase.auth.uid
            if (uid == null) {
                startActivity(Intent(this, SigninActivity::class.java))
            }
        }
    }
}