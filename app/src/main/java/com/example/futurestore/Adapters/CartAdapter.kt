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
        var price=item.price; var quantity=item.quantity
        viewHolder.itemView.cart_item_product_name_text_view.text=item.name
        viewHolder.itemView.cart_item_product_price_text_view.text="Price: ${item.price}  SR"
        viewHolder.itemView.cart_item_product_department_text_view.text=item.category
        viewHolder.itemView.cart_item_quantity_text_view.text="Quantity: $quantity"
        viewHolder.itemView.cart_item_total_text_view.text="Total: ${quantity * price} SR"
        Picasso.get().load(item.imageLink).into(viewHolder.itemView.cart_item_product_image_view)
    }
}