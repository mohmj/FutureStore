package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.MessageFromUserAdapter
import com.example.futurestore.Adapters.MessageToUserAdapter
import com.example.futurestore.Models.ContactMessage
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        var adapter=GroupAdapter<ViewHolder>()
        var uid=Firebase.auth.uid
        var reference= Firebase.database.getReference("chats/$uid/messages")
        var userChatReference=Firebase.database.getReference("users/$uid/chat/messages")
        contact_activity_message_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        userChatReference.addChildEventListener(object:ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                var message=snapshot.getValue(ContactMessage::class.java)
                if(message != null){
                    if(message.uid==uid.toString()){
                        adapter.add(MessageToUserAdapter(message))
                    }else{
                        adapter.add(MessageFromUserAdapter(message))
                    }
                }
                contact_activity_message_recycler_view.adapter=adapter
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var message=snapshot.getValue(ContactMessage::class.java)
                if(message != null){
                    if(message.uid==uid.toString()){
                        adapter.add(MessageToUserAdapter(message))
                    }else{
                        adapter.add(MessageFromUserAdapter(message))
                    }
                }
                contact_activity_message_recycler_view.adapter=adapter
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })


        contact_activity_send_message_button.setOnClickListener(){
            var message=contact_activity_message_edit_text.text.toString()
            if(message.isNotEmpty()){
                reference.push().setValue(ContactMessage(uid.toString(),message))
                userChatReference.push().setValue(ContactMessage(uid.toString(),message))
            }
            contact_activity_message_edit_text.setText("")

        }
    }
}