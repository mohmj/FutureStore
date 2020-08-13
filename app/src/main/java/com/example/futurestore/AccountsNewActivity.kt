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
import com.example.futurestore.Models.UserInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_accounts_new.*
import java.util.*

class AccountsNewActivity : AppCompatActivity() {

    var selectImageUri: Uri? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts_new)

        var uid=Firebase.auth.uid
        var uuid= UUID.randomUUID().toString()
        Firebase.database.getReference("users").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var user=it.getValue(UserInformation::class.java)
                    if(user?.uid==uid){
                        accounts_new_activity_phone_number_edit_text.setText(user?.phoneNumber)
                    }
                }
            }

        })

        var productCategory= Database().buy_and_sale_accounts

        accounts_new_activity_button.setOnClickListener() {

            val progressDialog= ProgressDialog(this)
            progressDialog.setTitle("Wait")
            progressDialog.setMessage("Please wait account is adding now")
            progressDialog.show()

            var productName = accounts_new_activity_name_edit_text.text.toString()
            var productPrice = accounts_new_activity_price_edit_text.text.toString()
            var productDescription = accounts_new_activity_description_edit_text.text.toString()
            var phoneNumber=accounts_new_activity_phone_number_edit_text.text.toString()
            var quantity="1"
            var storageReference = Firebase.storage.getReference("$productCategory/$uid/$productName")
            if (productName.isNotEmpty() && productPrice.isNotEmpty() && productDescription.isNotEmpty() && quantity.isNotEmpty()) {
                storageReference.putFile(selectImageUri!!).addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener {
                        var productImageLink=it.toString()
                        var reference = Firebase.database.getReference("products/$productCategory/$uuid")
                        reference.setValue(
                            AccountInformaion(
                                uid.toString(), uuid, productName,productDescription,productPrice,productImageLink,phoneNumber

                            )
                        ).addOnSuccessListener {
                            Firebase.database.getReference("users/$uid/$productCategory/$uuid").setValue(
                                AccountInformaion(
                                    uid.toString(), uuid, productName,productDescription,productPrice,productImageLink,phoneNumber

                                )
                            )
                            progressDialog.hide()
                            Toast.makeText(this, "The item was added successfully", Toast.LENGTH_SHORT)
                                .show()
                            var intent= Intent(this,MainActivity::class.java)
                            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        }.addOnFailureListener() {
                            progressDialog.hide()
                            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill data and choose image.", Toast.LENGTH_SHORT)
                    .show()
                progressDialog.hide()
            }
        }
        //----------------------------------------------------------------------------------------------------------------------
        // Select photo
        accounts_new_activity_select_image_button.setOnClickListener() {
            var photoIntent = Intent(Intent.ACTION_PICK);
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, 0)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            selectImageUri = data.data
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectImageUri);
            var bitmapDrawable = BitmapDrawable(bitmap)
            accounts_new_activity_select_image_button.text = ""
            accounts_new_activity_select_image_button.setBackgroundDrawable(bitmapDrawable)

        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    }