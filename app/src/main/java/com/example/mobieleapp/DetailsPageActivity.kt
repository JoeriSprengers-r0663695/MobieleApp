package com.example.mobieleapp

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.dorm.*
import com.example.mobieleapp.data.database.user.User
import com.example.mobieleapp.data.database.user.UserFireBase
import com.example.mobieleapp.data.database.user.UserViewModel
import com.example.mobieleapp.data.database.user.UserViewModelFactory
import com.example.myfirstapp.MainViewModel
import com.example.myfirstapp.MainViewModelFactory
import com.example.myfirstapp.repository.Repository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.io.Serializable
import kotlinx.android.synthetic.main.fragment_detailspage.*
import java.lang.Exception

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


        for (i in 0..5) {
            val imageref = Firebase.storage.reference.child("dorm/" + kot.adTitle + "/pic"+i)

            imageref.downloadUrl.addOnSuccessListener { Uri ->



                    val imageView = ImageView(this)
                    imageView.layoutParams = LinearLayout.LayoutParams(800, 600) // value is in pixels
                    imageView.setImageResource(R.mipmap.ic_launcher)
                    linearForImage.addView(imageView)
                    val imageURL = Uri.toString()
                    /*  Glide.with(itemView).load(imageURL).into(imagev)*/
                    Picasso.get().load(imageURL).into(imageView);

            }
        }
        var imgs2 = ArrayList<String>()
       /* try {
            for (i in 0..4) {
                val imageref = Firebase.storage.reference.child("dorm/"+ kot.adTitle +"/pic" + i)
                imageref.downloadUrl.addOnSuccessListener {Uri->
                    val imageURL = Uri.toString()
                    imgs2.add(imageURL)
                    Log.d("download","download")

                }

            }
        }catch (e: StorageException){
            e.message
                 Log.d("geen fotos meer","geeeeeeeeeeeeeeeeen")

        }


        Log.d("array",imgs2.toString())
*/




        if(kotOwner == u.username) {
            findViewById<Button>(R.id.btn_editDorm).visibility = View.VISIBLE
            findViewById<Button>(R.id.btn_deleteDorm).visibility = View.VISIBLE
        }


        findViewById<TextView>(R.id.txt_streetValue).text = kot.streetname + " " + kot.housenr.toString()
        findViewById<TextView>(R.id.txt_cityValue).text = kot.postalcode.toString() + ", " + kot.city
        findViewById<TextView>(R.id.txt_rentValue).text = "€" + kot.rent + " / month"
        findViewById<TextView>(R.id.txt_descriptionValue).text = kot.description

        //This part grabs the kotOwners' necessary data out of db
        var database = FirebaseDatabase.getInstance().reference.child("User")
        var getdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (d in snapshot.children) run {

                            var d = d.value as HashMap<String, String>
                            /*Log.d("kotowner", kotOwner.toString())
                            Log.d("d", d["username"].toString())*/


                            if(d["username"].equals(kotOwner)) {
                                Log.d("im inside if", "inside")
                                var email: String? = d["email"]
                                var phoneNr: String? = d["phoneNr"]

                                findViewById<TextView>(R.id.txt_emailValue).text = email
                                findViewById<TextView>(R.id.txt_phoneNumberValue).text = phoneNr
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdata)





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
            val intent = Intent(applicationContext, UpdateDormActivity::class.java).putExtra("kot", kot.adTitle)
            finish()
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_deleteDorm).setOnClickListener {
            var databasedel = FirebaseDatabase.getInstance().reference.child("Dorm")
            databasedel.ref.child(kot.adTitle).removeValue()
            val intent = Intent(applicationContext, DormListActivity::class.java)
            startActivity(intent)
        }


    }
}