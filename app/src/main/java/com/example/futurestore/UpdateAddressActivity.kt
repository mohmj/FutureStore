package com.example.futurestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.futurestore.Models.Location
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_address_new.*
import kotlinx.android.synthetic.main.activity_update_address.*

class UpdateAddressActivity : AppCompatActivity() {
    private var arrayAdapterForCity: ArrayAdapter<String>? = null
    private var arrayAdapterForTime: ArrayAdapter<String>? = null
    private var cityLists = arrayOf("")
    private var timeList = arrayOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_address)

        var uid = Firebase.auth.uid
        var locationType = intent.getParcelableExtra<Location>(Database().updateLocation)?.locationType
        var city =intent.getParcelableExtra<Location>(Database().updateLocation)?.city
        var time = intent.getParcelableExtra<Location>(Database().updateLocation)?.time
        var district = intent.getParcelableExtra<Location>(Database().updateLocation)?.district
        var street = intent.getParcelableExtra<Location>(Database().updateLocation)?.street
        var near = intent.getParcelableExtra<Location>(Database().updateLocation)?.near
        var name = intent.getParcelableExtra<Location>(Database().updateLocation)?.name
        var phoneNumber = intent.getParcelableExtra<Location>(Database().updateLocation)?.phoneNumber
        var lat = intent.getParcelableExtra<Location>(Database().updateLocation)?.lat
        var long = intent.getParcelableExtra<Location>(Database().updateLocation)?.long

        update_address_activity_distrect_edit_text.setText(district)
        update_address_activity_street_edit_text.setText(street)
        update_address_activity_near_edit_text.setText(near)
        update_address_activity_name_edit_text.setText(name)
        update_address_activity_phone_number_edit_text.setText(phoneNumber)



        //------------------------------------------------------------------------------------------------------------------------
        //Spinner for city .
        cityLists = arrayOf("Riyadh", "Jedda", "Dammam")
        arrayAdapterForCity =
            ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, cityLists!!)
        update_address_activity_city_spinner.adapter = arrayAdapterForCity
        update_address_activity_city_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    city = cityLists[p2]
                }
            }
//        //------------------------------------------------------------------------------------------------------------------------
//        //Radio button for select location type .
        update_address_activity_type_radio_group.setOnCheckedChangeListener() { group, checkedID ->
            when (checkedID) {
                R.id.new_address_activity_home_radio_button -> locationType = "Home"
                R.id.new_address_activity_work_radio_button -> locationType = "Work"
                R.id.new_address_activity_relax_radio_button -> locationType = "Relax"
            }
        }
//
//        //------------------------------------------------------------------------------------------------------------------------
//        //Radio button for select location type .
        timeList = arrayOf("9am - 2pm", "2pm - 5pm", "5pm - 9am")
        arrayAdapterForTime =
            ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, timeList!!)
        update_address_activity_time_spinner.adapter = arrayAdapterForTime
        update_address_activity_time_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    time = timeList[p2]
                }
            }
//        //------------------------------------------------------------------------------------------------------------------------
//        //Upload location to database
        update_address_activity_update_button.setOnClickListener() {
            var uid = Firebase.auth.uid.toString()
            district = update_address_activity_distrect_edit_text.text.toString()
            street = update_address_activity_street_edit_text.text.toString()
            near = update_address_activity_near_edit_text.text.toString()
            name = update_address_activity_name_edit_text.text.toString()
            phoneNumber = update_address_activity_phone_number_edit_text.text.toString()

                Firebase.database.getReference("users/$uid/addresses/$locationType").setValue(
                    Location(
                        lat.toString().toDouble(),
                        long.toString().toDouble(),
                        city.toString(),
                        locationType.toString(),
                        district.toString(),
                        street.toString(),
                        near.toString(),
                        time.toString(),
                        name.toString(),
                        phoneNumber.toString()
                    )
                ).addOnSuccessListener {
                    Toast.makeText(this, "The new location was updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }

        update_address_activity_delete_button.setOnClickListener(){
            Firebase.database.getReference("users/$uid/addresses/${locationType.toString()}").removeValue().addOnSuccessListener {
                Toast.makeText(this, "The new location was deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    }



