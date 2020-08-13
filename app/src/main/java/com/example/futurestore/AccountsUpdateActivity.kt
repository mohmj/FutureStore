package com.example.futurestore

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.futurestore.Models.AccountInformaion
import com.example.futurestore.Services.Database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_accounts_new.*
import kotlinx.android.synthetic.main.activity_accounts_update.*
import kotlinx.android.synthetic.main.activity_update_address.*


class AccountsUpdateActivity : AppCompatActivity() {

//    var selectImageUri: Uri? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts_update)
        var sellerId=intent.getParcelableExtra<AccountInformaion>(Database().update_account_intent)?.sellerId
        var ID=intent.getParcelableExtra<AccountInformaion>(Database().update_account_intent)?.ID
        var gameName=intent.getParcelableExtra<AccountInformaion>(Database().update_account_intent)?.gameName
        accounts_update_activity_name_edit_text.setText(gameName.toString())
        var descripe=intent.getParcelableExtra<AccountInformaion>(Database().update_account_intent)?.descripe
        accounts_update_activity_description_edit_text.setText(descripe.toString())
        var price=intent.getParcelableExtra<AccountInformaion>(Database().update_account_intent)?.price
        accounts_update_activity_price_edit_text.setText(price.toString())
        var imageLink=intent.getParcelableExtra<AccountInformaion>(Database().update_account_intent)?.imageLink
        Picasso.get().load(imageLink).into(accounts_update_activity_select_image_button)
        var phoneNumber=intent.getParcelableExtra<AccountInformaion>(Database().update_account_intent)?.phoneNumber
        accounts_update_activity_phone_number_edit_text.setText(phoneNumber.toString())
        var productCategory=Database().buy_and_sale_accounts




        update_accounts_activity_delete_button.setOnClickListener(){
            Firebase.storage.getReference("$productCategory/$sellerId/$gameName").delete()
            Firebase.database.getReference("products/$productCategory/$ID").removeValue()
            Firebase.database.getReference("users/$sellerId/$productCategory/$ID").removeValue()
            finish()
            Toast.makeText(this,"The account was deletes successfully",Toast.LENGTH_SHORT).show()
        }


//        update_accounts_activity_update_button.setOnClickListener(){
//            Toast.makeText(this,"Cooming soon",Toast.LENGTH_SHORT).show()
//            val progressDialog= ProgressDialog(this)
//            progressDialog.setTitle("Wait")
//            progressDialog.setMessage("Please wait account is adding now")
//            progressDialog.show()
//
//            var productName = accounts_update_activity_name_edit_text.text.toString()
//            var productPrice = accounts_update_activity_price_edit_text.text.toString()
//            var productDescription = accounts_update_activity_description_edit_text.text.toString()
//            var phoneNumber=accounts_update_activity_phone_number_edit_text.text.toString()
//            var quantity="1"
//            var storageReference = Firebase.storage.getReference("$productCategory/$sellerId/$productName")
//            if (productName.isNotEmpty() && productPrice.isNotEmpty() && productDescription.isNotEmpty() && quantity.isNotEmpty()) {
//                storageReference.putFile(selectImageUri!!).addOnSuccessListener {
//                    storageReference.downloadUrl.addOnSuccessListener {
//                        var productImageLink=it.toString()
//                        var reference = Firebase.database.getReference("products/$productCategory/$ID")
//                        reference.setValue(
//                            AccountInformaion(
//                                sellerId.toString(), ID.toString(), productName,productDescription,productPrice,productImageLink,phoneNumber
//
//                            )
//                        ).addOnSuccessListener {
//                            Firebase.database.getReference("users/$sellerId/$productCategory/$ID").setValue(
//                                AccountInformaion(
//                                    sellerId.toString(), ID.toString(), productName,productDescription,productPrice,productImageLink,phoneNumber
//
//                                )
//                            )
//                            progressDialog.hide()
//                            Toast.makeText(this, "The item was added successfully", Toast.LENGTH_SHORT)
//                                .show()
//                            var intent= Intent(this,MainActivity::class.java)
//                            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            startActivity(intent)
//
//                        }.addOnFailureListener() {
//                            progressDialog.hide()
//                            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Please fill data and choose image.", Toast.LENGTH_SHORT)
//                    .show()
//                progressDialog.hide()
//            }
        }
        //----------------------------------------------------------------------------------------------------------------------
        // Select photo
//        accounts_new_activity_select_image_button.setOnClickListener() {
//            var photoIntent = Intent(Intent.ACTION_PICK);
//            photoIntent.type = "image/*"
//            startActivityForResult(photoIntent, 0)
//        }
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
//
//            selectImageUri = data.data
//            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectImageUri);
//            var bitmapDrawable = BitmapDrawable(bitmap)
//            accounts_update_activity_select_image_button.setBackgroundDrawable(bitmapDrawable)
//
//        }
//    }
    //----------------------------------------------------------------------------------------------------------------------
