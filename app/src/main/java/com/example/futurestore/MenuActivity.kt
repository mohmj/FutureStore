package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.futurestore.Models.UserInformation
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_menu.*

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

            menu_activity_cart_button.setOnClickListener(){
                //Nothing until now
            }
            menu_activity_wish_list_button.setOnClickListener(){
                //Nothing until now
            }
            menu_activity_computer_button.setOnClickListener(){
                var intent=Intent(this,MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            menu_activity_laptop_button.setOnClickListener(){
                category("laptop",resources.getString(R.string.laptop))
            }
            menu_activity_console_button.setOnClickListener(){
                category("console",resources.getString(R.string.console))
            }
            menu_activity_program_and_game_button.setOnClickListener(){
                category("programs_and_games",resources.getString(R.string.programs_and_games))
            }
            menu_activity_buy_and_sale_accounts_button.setOnClickListener(){
                category("buy_and_sale_account",resources.getString(R.string.buy_and_sale_account))
            }
            menu_activity_instagram_button.setOnClickListener(){
                //Nothing until now
            }
            menu_activity_twitter_button.setOnClickListener(){
                //Nothing until now
            }
            menu_activity_web_button.setOnClickListener(){
                //Nothing until now
            }
            menu_activity_profile_button.setOnClickListener(){
            startActivity(Intent(this,ProfileActivity::class.java))
            }
            menu_activity_chat_button.setOnClickListener(){
                //Nothing until now
            }

}


    fun category(category:String, categoryTitle:String){
        var intent=Intent(this,ProductActivity::class.java)
        intent.putExtra(Database().category,category)
        intent.putExtra(Database().categoryTitle,categoryTitle)
        startActivity(intent)
    }
}


