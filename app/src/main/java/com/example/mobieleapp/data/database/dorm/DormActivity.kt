package com.example.mobieleapp.data.database.dorm

import com.example.mobieleapp.data.database.user.User

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.*

class DormActivity : AppCompatActivity() {

    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dorm)

        val button = findViewById<Button>(R.id.bevestigDorm)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (findViewById<EditText>(R.id.StreetnameValue).text.isEmpty() ||
                findViewById<EditText>(R.id.HousenrValue).text.toString().toInt() < 1  ||
                findViewById<EditText>(R.id.HousenrValue).text.isEmpty() ||
                findViewById<EditText>(R.id.CityValue).text.isEmpty() ||
                findViewById<EditText>(R.id.PostalcodeValue).text.toString().toInt() in 10001..999 ||
                findViewById<EditText>(R.id.PostalcodeValue).text.isEmpty() ||
                findViewById<EditText>(R.id.RentValue).text.isEmpty() ||
                findViewById<EditText>(R.id.RentValue).text.toString().toDouble() < 0  ||
                findViewById<EditText>(R.id.DescriptionValue).text.isEmpty()){

                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {

                val streetName = findViewById<EditText>(R.id.StreetnameValue).text.toString()

                val housenr = findViewById<EditText>(R.id.HousenrValue).text.toString().toInt()

                val city = findViewById<EditText>(R.id.CityValue).text.toString()

                val postalcode = findViewById<EditText>(R.id.PostalcodeValue).text.toString().toInt()

                val rent = findViewById<EditText>(R.id.RentValue).text.toString().toDouble()

                val description = findViewById<EditText>(R.id.DescriptionValue).text.toString()

                dormViewModel.allDorms.observe(this) {dorm ->
                    for (i in dorm) {
                    Log.d("dormBefore",i.streetname.toString())
                    }
                }

                val dorm = Dorm(null,streetName, housenr, city, postalcode, rent, description )
                dormViewModel.insert( dorm)
                finish()
            }
        }
    }
}
