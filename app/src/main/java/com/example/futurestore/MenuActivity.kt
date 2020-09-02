package com.example.futurestore

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.futurestore.Models.UserInformation
import com.example.futurestore.Services.Categories
import com.example.futurestore.Services.Database
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dark_mode_dialog.*
import kotlinx.android.synthetic.main.activity_menu.*
import java.util.*

class MenuActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)



        val uid=Firebase.auth.uid
        menu_activity_name_text_view.text=Database().user_name


        if(uid==null) {
            menu_activity_name_text_view.text=resources.getString(R.string.Login)
            menu_activity_name_text_view.setOnClickListener() {
                startActivity(Intent(this, SigninActivity::class.java))
            }
        }else{

            var reference=Firebase.database.getReference("users")
            reference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach(){
                        var user=it.getValue(UserInformation::class.java)
                        if(user != null){
                            if(user.uid==uid){
                                menu_activity_name_text_view.text=user.name
                                Database().user_name=user.name
                            }
                        }
                    }
                }

            })

        }

        var category=Categories()

            menu_activity_cart_button.setOnClickListener(){
                startActivity(Intent(this,CartActivity::class.java))
            }
            menu_activity_wish_list_button.setOnClickListener(){
                startActivity(Intent(this,WishlistActivity::class.java))
            }
            menu_activity_computer_button.setOnClickListener(){
                var intent=Intent(this,MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            menu_activity_laptop_button.setOnClickListener(){
                category(category.laptop,resources.getString(R.string.laptop))
            }
//            menu_activity_console_button.setOnClickListener(){
//                category(category.console,resources.getString(R.string.console))
//            }
//            menu_activity_program_and_game_button.setOnClickListener(){
//                category(category.programs_and_games,resources.getString(R.string.programs_and_games))
//            }
//            menu_activity_buy_and_sale_accounts_button.setOnClickListener(){
//                startActivity(Intent(this,AccountsActivity::class.java))
//            }
        menu_activity_web_button.setOnClickListener(){
            val url = "http://www.softwareclinics.com/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        menu_activity_twitter_button.setOnClickListener(){
            val url = "https://www.twitter.com/abdullahrayes_"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        menu_activity_instagram_button.setOnClickListener(){
            val url = "https://www.instagram.com/ironmj_/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        menu_activity_profile_button.setOnClickListener(){
            startActivity(Intent(this,ProfileActivity::class.java))
            }
            menu_activity_chat_button.setOnClickListener(){
                var uid=Firebase.auth.uid
                if(uid==null){
                    startActivity(Intent(this, SigninActivity::class.java))
                }else{
                    var userReference=Firebase.database.getReference("users")
                    userReference.addListenerForSingleValueEvent(object:ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var userName=""
                            var userEmail=""
                            var userPhoneNumber=""
                            snapshot.children.forEach {
                                var user=it.getValue(UserInformation::class.java)
                                if(user != null){
                                    if(user.uid==uid.toString()){
                                        userName=user.name
                                        userEmail=user.email
                                        userPhoneNumber=user.phoneNumber
                                    }
                                }
                            }
                            Firebase.database.getReference("chats/chat_users/$uid").setValue(UserInformation(uid.toString(),userName,userEmail,userPhoneNumber))
                        }

                    })
                    startActivity(Intent(this,ContactActivity::class.java))
                }
            }


//            menu_activity_night_mood_button.setOnClickListener(){
//                val mDialog = BottomSheetDialog(this)
//                mDialog.setContentView(layoutInflater.inflate(R.layout.activity_dark_mode_dialog,null))
//                mDialog.show()
//          }


}


    fun category(category:String, categoryTitle:String){
        var intent=Intent(this,ProductActivity::class.java)
        intent.putExtra(Database().category,category)
        intent.putExtra(Database().categoryTitle,categoryTitle)
        startActivity(intent)
    }
}


