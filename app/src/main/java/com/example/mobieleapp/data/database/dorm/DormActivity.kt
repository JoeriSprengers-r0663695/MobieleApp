package com.example.mobieleapp.data.database.dorm

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import kotlinx.android.synthetic.main.activity_dorm.*

class DormActivity : AppCompatActivity() {

    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }

    //store uris of picked images Needs to
    private var images: MutableList<String?>? = null

    //current position/index of selected images
    private var position = 0
    //request code to pick image(s)
    private val PICK_IMAGES_CODE = 0;



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



        //pick images clicking this button
        btn_selectImages.setOnClickListener {
            pickImagesIntent()
        }



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

    private fun pickImagesIntent() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Image(s)"), PICK_IMAGES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGES_CODE) {

            if(resultCode == Activity.RESULT_OK) {
                if(data!!.clipData != null) {
                    //picked multiple images
                    //get number of picked images
                    val count = data.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        //add image to list
                        images!!.add(imageUri.toString())
                    }
                }
                else {
                    //picked single image
                    val imageUri = data.data
                    //add single image
                    images!!.add(imageUri.toString())
                }
            }

        }
    }

}
