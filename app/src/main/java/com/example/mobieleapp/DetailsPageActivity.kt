package com.example.mobieleapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobieleapp.data.database.dorm.Dorm
import com.example.myfirstapp.MainViewModel
import com.example.myfirstapp.MainViewModelFactory
import com.example.myfirstapp.repository.Repository
import java.io.Serializable

class DetailsPageActivity : AppCompatActivity(),Serializable {

    //initializations and declarations for the Geocoding part
    private lateinit var viewModel: MainViewModel
    private val repository = Repository()
    private val viewModelFactory = MainViewModelFactory(repository)
    private var lat : String = ""
    private var long : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detailspage)
        //setSupportActionBar(findViewById(R.id.toolbar))

        var kot  = intent.getSerializableExtra("kot") as Dorm


        findViewById<TextView>(R.id.txt_streetValue).text = kot.streetname + " " + kot.housenr.toString()
        findViewById<TextView>(R.id.txt_cityValue).text = kot.postalcode.toString() + ", " + kot.city
        findViewById<TextView>(R.id.txt_rentValue).text = "€" + kot.rent.toString() + " / month"
        findViewById<TextView>(R.id.txt_emailValue).text = "email@domain.poop"
        findViewById<TextView>(R.id.txt_phoneNumberValue).text = "0123456789"
        findViewById<TextView>(R.id.txt_descriptionValue).text = kot.description





        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost(kot.streetname + " " + kot.housenr.toString() + ", " + kot.postalcode.toString() + " " + kot.city)
        viewModel.myResponse.observe(this, Observer { response ->

            //assigning response data value to global vars for later use
            lat = response.body()?.data?.get(0)?.latitude.toString()
            long = response.body()?.data?.get(0)?.longitude.toString()

        })




        findViewById<Button>(R.id.btn_showOnMap).setOnClickListener {

            //val action = detailsPageDirections.actionDetailsPageToMapsFragment(lat, long)
            val intent = Intent(applicationContext, mapsActivity::class.java).putExtra("lat",lat).putExtra("long",long)
            startActivity(intent)

        }
    }
}