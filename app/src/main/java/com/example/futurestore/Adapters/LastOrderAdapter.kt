package com.example.futurestore.Adapters

import com.example.futurestore.Models.LastOrderInformation
import com.example.futurestore.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.order_item.view.*

class LastOrderAdapter (var order:LastOrderInformation): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.order_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.order_item_date_text_view.text="Date: "+order.time
        viewHolder.itemView.order_item_status_text_view.text="Status: "+order.status
        viewHolder.itemView.order_item_price_text_view.text="Price: "+order.total
    }
}