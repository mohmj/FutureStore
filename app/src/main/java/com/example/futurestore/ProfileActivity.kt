package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val uid= Firebase.auth.uid

        profile_activity_personal_data_container.setOnClickListener(){
            startActivity(Intent(this,PersonalDataActivity::class.java))
        }

        profile_activity_last_oreders_container.setOnClickListener(){
            startActivity(Intent(this,LastOredersActivity::class.java))
        }

//        profile_activity_return_and_warranty_container.setOnClickListener(){
//            //Nothing until now
//        }

        profile_activity_addresses_container.setOnClickListener(){
            startActivity(Intent(this,AddressesActivity::class.java))
        }

//        profile_activity_seller_account_container.setOnClickListener(){
//            //Nothing until now
//        }


        if(uid != null){
            profile_activity_big_button_background_text_view.setBackgroundResource(R.drawable.profile_signout_button)
            profile_activity_big_button_text_view.text=resources.getString(R.string.sign_out)
            profile_activity_big_button_background_text_view.setOnClickListener(){
                signoutProfile()
            }
            profile_activity_big_button_text_view.setOnClickListener(){
                signoutProfile()
            }
        }else{
            profile_activity_big_button_background_text_view.setBackgroundResource(R.drawable.profile_signin_button)
            profile_activity_big_button_text_view.text=resources.getString(R.string.Login)
            profile_activity_big_button_background_text_view.setOnClickListener(){
                signinProfile()
            }
            profile_activity_big_button_text_view.setOnClickListener(){
                signinProfile()
            }
        }





    }

    fun signoutProfile(){
        val auth=Firebase.auth
        auth.signOut()
        var mainIntent= Intent(this,MainActivity::class.java)
        mainIntent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mainIntent)
        Database().user_name=""
        Toast.makeText(this,resources.getString(R.string.sign_out_successful),Toast.LENGTH_SHORT).show()
    }

    fun signinProfile(){
        startActivity(Intent(this,SigninActivity::class.java))
    }
}