package com.example.futurestore.Adapters

import com.example.futurestore.Models.AddressInformation
import com.example.futurestore.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.location_item.view.*

class ShowLocationAdapter(var location:AddressInformation): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.location_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        var lat=location.lat
        var long=location.long
        viewHolder.itemView.location_item_location_type_text_view.text=location.locationType
        viewHolder.itemView.location_item_city_text_view.text=location.city
        viewHolder.itemView.location_item_district_text_view.text=location.district
        viewHolder.itemView.location_item_street_text_view.text=location.street
        viewHolder.itemView.location_item_near_text_view.text=location.near
        viewHolder.itemView.location_item_time_text_view.text=location.time
        viewHolder.itemView.location_item_name_text_view.text=location.name
        viewHolder.itemView.location_item_phone_number_text_view.text=location.phoneNumber
    }
}