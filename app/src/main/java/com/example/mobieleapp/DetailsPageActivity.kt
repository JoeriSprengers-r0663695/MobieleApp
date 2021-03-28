package com.example.mobieleapp

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.dorm.*
import com.example.mobieleapp.data.database.user.User
import com.example.mobieleapp.data.database.user.UserFireBase
import com.example.mobieleapp.data.database.user.UserViewModel
import com.example.mobieleapp.data.database.user.UserViewModelFactory
import com.example.myfirstapp.MainViewModel
import com.example.myfirstapp.MainViewModelFactory
import com.example.myfirstapp.repository.Repository
import com.google.gson.Gson
import java.io.Serializable
import kotlinx.android.synthetic.main.fragment_detailspage.*

class DetailsPageActivity : AppCompatActivity(),Serializable {

    //initializations and declarations for the Geocoding part
    private lateinit var viewModel: MainViewModel
    private val repository = Repository()
    private val viewModelFactory = MainViewModelFactory(repository)
    private var lat : Double = 0.0
    private var long : Double = 0.0

    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detailspage)

        var kot  = intent.getSerializableExtra("kot") as Dorm

        var kotOwner  = kot.User

        //Code for current user
        val gson = Gson()
        val json: String? = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString("user", "")
        val u: UserFireBase = gson.fromJson(json, UserFireBase::class.java)



        //slideshow list and such
        var imgs = listOf<Int>(R.mipmap.ic_placeh1,R.mipmap.ic_placeh2,R.mipmap.ic_placeh3)


        var slideshowAdapter = SlideshowAdapater(imgs,this)
        pager.adapter = slideshowAdapter




        if(kotOwner == u.username) {
            findViewById<Button>(R.id.btn_editDorm).visibility = View.VISIBLE
            findViewById<Button>(R.id.btn_deleteDorm).visibility = View.VISIBLE
        }


        findViewById<TextView>(R.id.txt_streetValue).text = kot.streetname + " " + kot.housenr.toString()
        findViewById<TextView>(R.id.txt_cityValue).text = kot.postalcode.toString() + ", " + kot.city
        findViewById<TextView>(R.id.txt_rentValue).text = "€" + kot.rent + " / month"
      /*  findViewById<TextView>(R.id.txt_emailValue).text = kotOwner.email
        findViewById<TextView>(R.id.txt_phoneNumberValue).text = kotOwner.phoneNr*/
        findViewById<TextView>(R.id.txt_descriptionValue).text = kot.description





        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost(kot.streetname + " " + kot.housenr.toString() + ", " + kot.postalcode.toString() + " " + kot.city)
        viewModel.myResponse.observe(this, Observer { response ->

            //assigning response data value to global vars for later use
            lat = response.body()!!.data[0].latitude
            long = response.body()!!.data[0].longitude

        })




        findViewById<Button>(R.id.btn_showOnMap).setOnClickListener {

            //val action = detailsPageDirections.actionDetailsPageToMapsFragment(lat, long)
            val intent = Intent(applicationContext, mapsActivity::class.java).putExtra("lat",lat).putExtra("long",long)
            startActivity(intent)

        }

        findViewById<Button>(R.id.btn_editDorm).setOnClickListener {
            val intent = Intent(applicationContext, UpdateDormActivity::class.java).putExtra("kot", kot)
            finish()
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_deleteDorm).setOnClickListener {
            dormViewModel.deleteSpecific(kot)
            val intent = Intent(applicationContext, DormListActivity::class.java)
            startActivity(intent)
        }


    }
}