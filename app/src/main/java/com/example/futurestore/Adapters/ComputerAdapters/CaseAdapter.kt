package com.example.futurestore.Adapters.ComputerAdapters

import android.content.ClipData
import com.example.futurestore.Models.Computer.ComputerCase
import com.example.futurestore.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.product_item.view.*

class CaseAdapter (var item:ComputerCase): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.product_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Picasso.get().load(item.imageLink).into(viewHolder.itemView.product_item_image_view)
        viewHolder.itemView.product_item_name_text_view.text=item.name
        viewHolder.itemView.product_item_price_text_view.text=item.price.toString()
    }
}