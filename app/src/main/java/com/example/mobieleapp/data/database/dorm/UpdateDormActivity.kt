package com.example.mobieleapp.data.database.dorm

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mobieleapp.DetailsPageActivity
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.Application
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UpdateDormActivity : AppCompatActivity() {
    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_dorm)
        var kotTitle  = intent.getSerializableExtra("kot") as String


        var dorm : DormFireBase
        var database = FirebaseDatabase.getInstance().reference.child("Dorm")
        var getdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (d in snapshot.children) run {

                        var d = d.value as HashMap<String, String>
                        /*Log.d("kotowner", kotOwner.toString())
                        Log.d("d", d["username"].toString())*/


                        if(d["adTitle"].equals(kotTitle)) {
                            var adTitle: String? =d.get("adTitle")
                            var streetName: String? =d.get("streetName")
                            var housenr: Long? =d.get("housenr")  as Long
                            var city: String? =d.get("city")
                            var postalcode: Long? =d.get("postalcode") as Long
                            var rent: String =d.get("rent") as String
                            var description: String?=d.get("description")
                            var owner: String?=d.get("owner")

                            findViewById<TextView>(R.id.txtv_updateDormAdTitle).setText(adTitle)
                            findViewById<EditText>(R.id.txtv_updateDormStreet).setText(streetName)
                            findViewById<EditText>(R.id.txtv_updateDormHouseNr).setText(housenr.toString())
                            findViewById<EditText>(R.id.txtv_updateDormCity).setText(city)
                            findViewById<EditText>(R.id.txtv_updateDormPostalcode).setText(postalcode.toString())
                            findViewById<EditText>(R.id.txtv_updateDormRent).setText(rent)
                            findViewById<EditText>(R.id.txtv_updateDormDescription).setText(description)


                            dorm = DormFireBase(adTitle,streetName,housenr,city,postalcode ,rent ,description,owner)



                            findViewById<Button>(R.id.btn_confirmUpdateDorm).setOnClickListener {
                                var newDorm = updateDorm(dorm)
                                if(newDorm !== null) {
                                    val intent =
                                        Intent(applicationContext, DetailsPageActivity::class.java).putExtra("kot",
                                            newDorm)
                                    finish()
                                    startActivity(intent)
                                }
                            }
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdata)







    }

    private fun updateDorm(kot: DormFireBase): Dorm?{
        var newTitle = findViewById<TextView>(R.id.txtv_updateDormAdTitle).text.toString()
        var newStreet =findViewById<EditText>(R.id.txtv_updateDormStreet).text.toString()
        var newHouseNr =findViewById<EditText>(R.id.txtv_updateDormHouseNr).text.toString().toLong()
        var newCity =findViewById<EditText>(R.id.txtv_updateDormCity).text.toString()
        var newPostalCode =findViewById<EditText>(R.id.txtv_updateDormPostalcode).text.toString().toLong()
        var newRent =findViewById<EditText>(R.id.txtv_updateDormRent).text.toString().toDouble()
        var newDescription =findViewById<EditText>(R.id.txtv_updateDormDescription).text.toString()

        if(validateFields(newTitle,newStreet,newHouseNr,newCity,newPostalCode,newRent,newDescription)) {
            var updatedDormFirebase = DormFireBase(kot.adTitle,newStreet,newHouseNr,newCity,newPostalCode,String.format("%.2f",newRent),newDescription, kot.owner)
            var databaseUpdate = FirebaseDatabase.getInstance().reference.child("Dorm")
            databaseUpdate.ref.child(kot.adTitle.toString()).setValue(updatedDormFirebase)

            //dormViewModel.updateDorm(updatedDorm)
            return kot.adTitle?.let { Dorm(it,newStreet,newHouseNr,newCity,newPostalCode,String.format("%.2f",newRent),newDescription, kot.owner) }
        } else {
            Toast.makeText(applicationContext,"you didnt fill in everything or at least not correct", Toast.LENGTH_LONG).show()
            return null
        }
    }

    fun validateFields(
        title:String,
        street:String,
        nr: Long,
        city:String,
        code: Long,
        rent:Double,
        description:String) : Boolean {
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
