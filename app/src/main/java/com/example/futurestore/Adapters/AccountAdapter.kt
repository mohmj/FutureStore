package com.example.futurestore.Adapters

import com.example.futurestore.Models.AccountInformaion
import com.example.futurestore.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.account_item.view.*

class AccountAdapter (var item:AccountInformaion): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.account_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.account_item_game_name_text_view.text=item.gameName
        viewHolder.itemView.account_item_descripe_text_view.text=item.descripe
        viewHolder.itemView.account_item_price_text_view.text=item.price
        Picasso.get().load(item.imageLink).into(viewHolder.itemView.account_item_image_view)
    }


}