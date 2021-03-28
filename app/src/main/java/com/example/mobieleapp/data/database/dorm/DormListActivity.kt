package com.example.mobieleapp.data.database.dorm

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.CameraActivity
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.user.User
import com.example.mobieleapp.data.database.user.UserFireBase
import com.example.mobieleapp.data.database.user.UserViewModel
import com.example.mobieleapp.data.database.user.UserViewModelFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson


class DormListActivity : AppCompatActivity() {


    private val newWordActivityRequestCode = 1
    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        val gson = Gson()
        val json: String? = getDefaultSharedPreferences(applicationContext).getString("user", "")
        val u: UserFireBase? = gson.fromJson(json, UserFireBase::class.java)
        if (u != null) {
            Log.d("user in current session",u.username.toString())
        }

        var database = FirebaseDatabase.getInstance().reference.child("Dorm")

        var getdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var dormlist:ArrayList<Dorm> = ArrayList()
                    var dorm : Dorm
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewDorms)
                    val adapter = DormListAdapter()

                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@DormListActivity)
                    for(d in snapshot.children) run {
                        var d = d.value as HashMap<String,String>

                        var adTitle: String? =d.get("adTitle")
                        var streetName: String? =d.get("streetName")
                        var housenr: Long? =d.get("housenr")  as Long
                        var city: String? =d.get("city")
                        var postalcode: Long? =d.get("postalcode") as Long
                        var rent: Double =d.get("rent") as Double
                        var description: String?=d.get("description")
                        var owner: String?=d.get("owner")

                        dorm = adTitle?.let { Dorm(it,streetName,housenr,city,postalcode ,rent ,description,owner) }!!

                        dormlist.add(dorm!!)

                    }
                    Log.d("see if he comes in this","ben hier")


                    adapter.submitList(dormlist)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }



        database.addValueEventListener(getdata)

        var user  = u
            Log.d("user", user.toString())

        if (user != null) {
            if(user.role.toString() == "Renter") {
                findViewById<Button>(R.id.addKotId).visibility = View.GONE
            }
        }


        var userTest = userViewModel.getById(0)
        Log.d("querrie", userTest.toString())



        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        /*dormViewModel.allDorms.observe(owner = this) { dorms ->
            // Update the cached copy of the dorms in the adapter// .
            //Log.d("woord",dorms.toString() )

            dorms.let { adapter.submitList(it) }
        }
*/


        findViewById<Button>(R.id.addKotId).setOnClickListener {
            val intent = Intent(this@DormListActivity, DormActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btn_toProfile).setOnClickListener {
            val intent = Intent(this@DormListActivity, CameraActivity::class.java)
            startActivity(intent)
        }

    }
}