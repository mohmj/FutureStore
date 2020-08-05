package com.example.futurestore.Adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.futurestore.Models.ContactMessage
import com.example.futurestore.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.message_from_user.view.*

class MessageFromUserAdapter(var message:ContactMessage): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.message_from_user
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.message_from_user_text_view.text=message.message
    }
}