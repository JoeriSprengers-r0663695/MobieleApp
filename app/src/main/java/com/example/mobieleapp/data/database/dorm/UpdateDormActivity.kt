package com.example.mobieleapp.data.database.dorm

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mobieleapp.DetailsPageActivity
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.Application

class UpdateDormActivity : AppCompatActivity() {
    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_dorm)
        var kot  = intent.getSerializableExtra("kot") as Dorm




        findViewById<EditText>(R.id.txtv_updateDormAdTitle).setText(kot.adTitle)
        findViewById<EditText>(R.id.txtv_updateDormStreet).setText(kot.streetname)
        findViewById<EditText>(R.id.txtv_updateDormHouseNr).setText(kot.housenr.toString())
        findViewById<EditText>(R.id.txtv_updateDormCity).setText(kot.city)
        findViewById<EditText>(R.id.txtv_updateDormPostalcode).setText(kot.postalcode.toString())
        findViewById<EditText>(R.id.txtv_updateDormRent).setText(kot.rent.toString())
        findViewById<EditText>(R.id.txtv_updateDormDescription).setText(kot.description)

        findViewById<Button>(R.id.btn_confirmUpdateDorm).setOnClickListener {
            var newDorm = updateDorm(kot)
            if(newDorm !== null) {
                val intent =
                    Intent(applicationContext, DetailsPageActivity::class.java).putExtra("kot",
                        newDorm)
                finish()
                startActivity(intent)
            }
        }


    }

    private fun updateDorm(kot: Dorm): Dorm?{
        var newTitle = findViewById<EditText>(R.id.txtv_updateDormAdTitle).text.toString()
        var newStreet =findViewById<EditText>(R.id.txtv_updateDormStreet).text.toString()
        var newHouseNr =findViewById<EditText>(R.id.txtv_updateDormHouseNr).text.toString().toInt()
        var newCity =findViewById<EditText>(R.id.txtv_updateDormCity).text.toString()
        var newPostalCode =findViewById<EditText>(R.id.txtv_updateDormPostalcode).text.toString().toInt()
        var newRent =findViewById<EditText>(R.id.txtv_updateDormRent).text.toString().toDouble()
        var newDescription =findViewById<EditText>(R.id.txtv_updateDormDescription).text.toString()


        if(validateFields(newTitle,newStreet,newHouseNr,newCity,newPostalCode,newRent,newDescription)) {
            var updatedDorm = Dorm(kot.idDorm, newTitle,newStreet,newHouseNr,newCity,newPostalCode,newRent,newDescription,kot.idUser)
            dormViewModel.updateDorm(updatedDorm)
            return updatedDorm
        } else {
            Toast.makeText(applicationContext,"you didnt fill in everything or at least not correct", Toast.LENGTH_LONG).show()
            return null
        }
    }

    fun validateFields(title:String,street:String,nr:Int,city:String,code:Int,rent:Double,description:String) : Boolean {
        return !(TextUtils.isEmpty(title) ||
                TextUtils.isEmpty(street) ||
                nr<1 ||
                TextUtils.isEmpty(city) ||
                code in 10001..999 ||
                rent < 0 ||
                TextUtils.isEmpty(description)
                )
    }
}
