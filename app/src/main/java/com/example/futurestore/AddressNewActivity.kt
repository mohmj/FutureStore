package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.futurestore.Models.AddressInformation
import com.example.futurestore.Models.UserInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_address_new.*

class AddressNewActivity : AppCompatActivity() {

    private var arrayAdapterForCity: ArrayAdapter<String>? = null
    private var arrayAdapterForTime: ArrayAdapter<String>? = null
    private var cityLists = arrayOf("")
    private var timeList = arrayOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_new)

        var uid = Firebase.auth.uid

        var city = "";
        var locationType = "";
        var time = "";
        var district = "";
        var street = "";
        var near = "";
        var name = "";
        var phoneNumber = "";
        var lat = 0.0;
        var long = 0.0

        Firebase.database.getReference("users").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var userInfo=it.getValue(UserInformation::class.java)
                    if(userInfo != null){
                        if(userInfo.uid==uid.toString()){
                            name=userInfo.name
                            new_address_activity_name_edit_text.setText(name)
                            phoneNumber=userInfo.phoneNumber
                            new_address_activity_phone_number_edit_text.setText(phoneNumber)
                        }
                    }
                }
            }

        })

        //------------------------------------------------------------------------------------------------------------------------
        //Spinner for city .
        cityLists = arrayOf("Riyadh", "Jedda", "Dammam")
        arrayAdapterForCity =
            ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, cityLists!!)
        new_address_activity_city_spinner.adapter = arrayAdapterForCity
        new_address_activity_city_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    city = cityLists[p2]
                }
            }
        //------------------------------------------------------------------------------------------------------------------------
        //Radio button for select location type .
        new_address_activity_type_radio_group.setOnCheckedChangeListener() { group, checkedID ->
            when (checkedID) {
                R.id.new_address_activity_home_radio_button -> locationType = "Home"
                R.id.new_address_activity_work_radio_button -> locationType = "Work"
                R.id.new_address_activity_relax_radio_button -> locationType = "Relax"
            }
        }

        //------------------------------------------------------------------------------------------------------------------------
        //Radio button for select location type .
        timeList = arrayOf("9am - 2pm", "2pm - 5pm", "5pm - 9am")
        arrayAdapterForTime =
            ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, timeList!!)
        address_activity_time_spinner.adapter = arrayAdapterForTime
        address_activity_time_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    time = timeList[p2]
                }
            }
        //------------------------------------------------------------------------------------------------------------------------
        //Upload location to database
        new_address_activity_button.setOnClickListener() {
            var uid = Firebase.auth.uid.toString()
            district = new_address_activity_distrect_edit_text.text.toString()
            street = new_address_activity_street_edit_text.text.toString()
            near = new_address_activity_near_edit_text.text.toString()
            name = new_address_activity_name_edit_text.text.toString()
            phoneNumber = new_address_activity_phone_number_edit_text.text.toString()


            if (district.isNotEmpty() && street.isNotEmpty() && near.isNotEmpty() && name.isNotEmpty() && phoneNumber.isNotEmpty()) {
                Firebase.database.getReference("users/$uid/addresses/$locationType").setValue(
                    AddressInformation(
                        lat,
                        long,
                        city,
                        locationType,
                        district,
                        street,
                        near,
                        time,
                        name,
                        phoneNumber
                    )
                ).addOnSuccessListener {
                    Toast.makeText(this, "The new location was added", Toast.LENGTH_SHORT).show()
                    finish()
                }


            }else{
                Toast.makeText(this, "Please fill your data.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}


