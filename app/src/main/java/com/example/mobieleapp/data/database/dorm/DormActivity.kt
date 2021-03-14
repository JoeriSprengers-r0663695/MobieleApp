package com.example.mobieleapp.data.database.dorm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.*
import com.example.mobieleapp.data.database.user.User
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson

class DormActivity : AppCompatActivity() {

    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dorm)

        var database = FirebaseDatabase.getInstance().reference.child("Dorm")

        val gson = Gson()
        val json: String? = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("user", "")
        val u: User = gson.fromJson(json, User::class.java)

        var user  = u
        var iduser = user.idUser
        val button = findViewById<Button>(R.id.bevestigDorm)


        button.setOnClickListener {
            val replyIntent = Intent()
            if (
                findViewById<EditText>(R.id.adTitleValue).text.isEmpty() ||
                findViewById<EditText>(R.id.StreetnameValue).text.isEmpty() ||
                findViewById<EditText>(R.id.HousenrValue).text.toString().toInt() < 1  ||
                findViewById<EditText>(R.id.HousenrValue).text.isEmpty() ||
                findViewById<EditText>(R.id.CityValue).text.isEmpty() ||
                findViewById<EditText>(R.id.PostalcodeValue).text.toString().toInt() in 10001..999 ||
                findViewById<EditText>(R.id.PostalcodeValue).text.isEmpty() ||
                findViewById<EditText>(R.id.RentValue).text.isEmpty() ||
                findViewById<EditText>(R.id.RentValue).text.toString().toDouble() < 0  ||
                findViewById<EditText>(R.id.DescriptionValue).text.isEmpty()){

                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            if(!findViewById<EditText>(R.id.RentValue).text.toString().contains(".")){
                Toast.makeText(applicationContext,
                "Rent must be a double!",
                Toast.LENGTH_SHORT).show()

            }
            if(findViewById<EditText>(R.id.RentValue).text.toString().contains(".00")){
                Toast.makeText(applicationContext,
                "cannot accept .00 because fuck it  ¯\\_(ツ)_/¯",
                Toast.LENGTH_SHORT).show()

            }


            else {

                val adTitle = findViewById<EditText>(R.id.adTitleValue).text.toString()
                val streetName = findViewById<EditText>(R.id.StreetnameValue).text.toString()

                val housenr = findViewById<EditText>(R.id.HousenrValue).text.toString().toLong()

                val city = findViewById<EditText>(R.id.CityValue).text.toString()

                val postalcode = findViewById<EditText>(R.id.PostalcodeValue).text.toString().toLong()

                val rent = findViewById<EditText>(R.id.RentValue).text.toString().toDouble()


                val description = findViewById<EditText>(R.id.DescriptionValue).text.toString()
                Log.d("toon de rent",(rent).toString())

                Log.d("toon de rent met 0 00",(rent.toString()))

                var dormFireBase = DormFireBase(adTitle, streetName, housenr, city, postalcode, rent , description, u.username)

                dormFireBase.adTitle?.let { it1 -> database.child(it1).setValue(dormFireBase) }

                dormViewModel.allDorms.observe(this) {dorm ->
                    for (i in dorm) {
                    Log.d("dormBefore",i.streetname.toString())
                    }
                }

              /*  val dorm = iduser?.let { it1 ->
                    Dorm(null,adTitle,streetName, housenr, city, postalcode, rent, description,
                        it1)
                }
                if (dorm != null) {
                    dormViewModel.insert( dorm)
                }*/
                finish()
            }
        }
    }
}
