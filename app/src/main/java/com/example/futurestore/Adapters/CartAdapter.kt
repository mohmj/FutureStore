package com.example.futurestore.Adapters

import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_cart_item.view.*

class CartAdapter (var item:ProductInformation): Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.activity_cart_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.cart_item_product_name_text_view.text=item.name
        viewHolder.itemView.cart_item_product_price_text_view.text="${item.price}  SR"
        viewHolder.itemView.cart_item_product_department_text_view.text=item.category
        Picasso.get().load(item.imageLink).into(viewHolder.itemView.cart_item_product_image_view)
    }

}