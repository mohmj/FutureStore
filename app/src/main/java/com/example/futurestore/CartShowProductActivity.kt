package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.R
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cart_show_product.*
import kotlinx.android.synthetic.main.activity_show_product.*

class CartShowProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_show_product)

        var uid=Firebase.auth.uid

        var productNumber=intent.getParcelableExtra<ProductInformation>("tesla")?.productNumber.toString()

        val productName=intent.getParcelableExtra<ProductInformation>("tesla")?.name.toString()
        cart_show_product_activity_name_text_view.text=productName

        val productCategory=intent.getParcelableExtra<ProductInformation>("tesla")?.category.toString()

        val productImageLink=intent.getParcelableExtra<ProductInformation>("tesla")?.imageLink.toString()
        Picasso.get().load(productImageLink).into(cart_show_product_activity_image_view)

        val productPrice=intent.getParcelableExtra<ProductInformation>("tesla")?.price.toString()
        cart_show_product_activity_price_text_view.text=productPrice

        val productDescription=intent.getParcelableExtra<ProductInformation>("tesla")?.description.toString()
        cart_show_product_activity_description_text_view.text=productDescription

        var productQuantity=0
        var quantity=intent.getParcelableExtra<ProductInformation>("tesla")?.quantity.toString().toInt()
        cart_show_product_activity_quantity_text_view.text=quantity.toString()
        Firebase.database.getReference("products/$productCategory").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var theQuantity=it.getValue(ProductInformation::class.java)
                    if(theQuantity != null){
                        if(theQuantity.productNumber==productNumber){
                            productQuantity=theQuantity.quantity
                        }
                    }
                }
            }
        })

        cart_show_product_activity_quantity_add_button.setOnClickListener(){
            if(quantity < productQuantity!!){
                quantity++
                cart_show_product_activity_quantity_text_view.text=quantity.toString()
            }else{
                Toast.makeText(this, "You can't add more than $quantity", Toast.LENGTH_SHORT).show()
            }
        }

        cart_show_product_activity_quantity_minus_button.setOnClickListener(){
            quantity--
            if(quantity==0){
                quantity=1
            }else{
                cart_show_product_activity_quantity_text_view.text=quantity.toString()
            }
        }


        cart_show_product_activity_delete_button.setOnClickListener(){
            var reference= Firebase.database.getReference("users/$uid/cart/$productNumber")
            reference.removeValue().addOnSuccessListener {
                Toast.makeText(this,"The item is deletes",Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
            }
        }

        cart_show_product_activity_add_wish_list_button.setOnClickListener(){
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



        cart_show_product_update_button.setOnClickListener(){
            Firebase.database.getReference("users/$uid/cart/$productNumber").setValue(ProductInformation(
                productNumber,
                productName,
                productCategory,
                productPrice.toDouble(),
                quantity,
                productImageLink,
                productDescription
            )
            ).addOnSuccessListener {
                finish()
            }
        }

    }
}