package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.futurestore.Models.ProductInformation
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
import kotlinx.android.synthetic.main.activity_wishlist_show_product.*

class WishlistShowProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist_show_product)

        var uid= Firebase.auth.uid

        val productNumber=intent.getParcelableExtra<ProductInformation>(Database().wishListIntent)?.productNumber.toString()


        val productName=intent.getParcelableExtra<ProductInformation>(Database().wishListIntent)?.name.toString()
        wish_list_show_product_activity_name_text_view.text=productName

        val productCategory=intent.getParcelableExtra<ProductInformation>(Database().wishListIntent)?.category.toString()
        Toast.makeText(this,productCategory,Toast.LENGTH_SHORT).show()

        val productImageLink=intent.getParcelableExtra<ProductInformation>(Database().wishListIntent)?.imageLink.toString()
        Picasso.get().load(productImageLink).into(wish_list_show_product_activity_image_view)

        val productPrice=intent.getParcelableExtra<ProductInformation>(Database().wishListIntent)?.price.toString()
        wish_list_show_product_activity_price_text_view.text=productPrice

        val productDescription=intent.getParcelableExtra<ProductInformation>(Database().wishListIntent)?.description.toString()
        wish_list_show_product_activity_description_text_view.text=productDescription

        var productQuantity=0
        Firebase.database.getReference("products/$productCategory").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var theQuantity=it.getValue(ProductInformation::class.java)
                    if(theQuantity!=null){
                        if (theQuantity.productNumber==productNumber){
                            productQuantity=theQuantity.quantity
                        }
                    }
                }
            }
        })

        var quantity=1

        wish_list_activity_quantity_add_button.setOnClickListener(){
            if(quantity < productQuantity!!){
                quantity++
                wish_list_activity_quantity_text_view.text=quantity.toString()
            }else{
                Toast.makeText(this, "You can't add more than $quantity", Toast.LENGTH_SHORT).show()
            }
        }

        wish_list_activity_quantity_minus_button.setOnClickListener(){
            quantity--
            if(quantity==0){
                quantity=1
            }else{
                wish_list_activity_quantity_text_view.text=quantity.toString()
            }
        }

        wish_list_show_product_activity_delete_button.setOnClickListener(){
            var reference= Firebase.database.getReference("users/$uid/wish_list/$productNumber")
            reference.removeValue().addOnSuccessListener {
                Toast.makeText(this,"The item is deletes", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,"${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        wish_list_show_product_activity_add_cart_button.setOnClickListener(){
            Firebase.database.getReference("users/$uid/cart").child(productNumber).setValue(
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