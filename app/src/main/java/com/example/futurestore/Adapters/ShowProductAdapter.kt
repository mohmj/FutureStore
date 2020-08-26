package com.example.futurestore.Adapters

import com.example.futurestore.Models.ProductInformation
import com.example.futurestore.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.product_item.view.*

class ShowProductAdapter(var product:ProductInformation): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.activity_show_product
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.product_item_name_text_view.text=product.name
        viewHolder.itemView.product_item_price_text_view.text=product.price.toString()
        Picasso.get().load(product.imageLink).into(viewHolder.itemView.product_item_image_view)

    }
}