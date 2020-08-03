package com.example.futurestore.Adapters

import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.product_item.view.*

class TestProudct(var info: ProductInformation): Item<ViewHolder>(){

    override fun getLayout(): Int {
        return R.layout.product_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.product_item_name_text_view.text=info.name
        viewHolder.itemView.product_item_price_text_view.text=info.price
        if(info.imageLink != ""){
            Picasso.get().load(info.imageLink).into(viewHolder.itemView.product_item_image_view)
        }

    }
}