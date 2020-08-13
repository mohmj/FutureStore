package com.example.futurestore

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.futurestore.Models.AccountInformaion
import com.example.futurestore.Services.Database
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_account_show.*

class AccountShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_show)

        Picasso.get().load(intent.getParcelableExtra<AccountInformaion>(Database().show_account_intent)?.imageLink).into(account_show_activity_image_view)
        account_show_activity_game_name_text_view.text=intent.getParcelableExtra<AccountInformaion>(Database().show_account_intent)?.gameName
        account_show_activity_price_text_view.text=intent.getParcelableExtra<AccountInformaion>(Database().show_account_intent)?.price
        account_show_activity_descripe_text_view.text=intent.getParcelableExtra<AccountInformaion>(Database().show_account_intent)?.descripe
        var phoneNumber=intent.getParcelableExtra<AccountInformaion>(Database().show_account_intent)?.phoneNumber
        account_show_activity_phone_number_text_view.text=phoneNumber


        account_show_activity_call_image_view.setOnClickListener(){
            val intent= Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+phoneNumber.toString().trim()))
            startActivity(intent)
        }

        account_show_activity_whatsup_image_view.setOnClickListener(){
            val intent=Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+phoneNumber.toString()))
            intent.setPackage("com.whatsapp")
            startActivity(intent)
        }
    }
}