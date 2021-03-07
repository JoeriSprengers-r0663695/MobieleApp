package com.example.mobieleapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.dorm.DormListAdapter
import com.example.mobieleapp.data.database.dorm.DormViewModel
import com.example.mobieleapp.data.database.dorm.DormViewModelFactory
import com.example.mobieleapp.data.database.user.User
import com.example.mobieleapp.data.database.user.UserViewModel
import com.example.mobieleapp.data.database.user.UserViewModelFactory
import com.google.gson.Gson
import java.io.ByteArrayOutputStream


class CameraActivity : AppCompatActivity() {
    lateinit var imagview : ImageView
    lateinit var btnCapture: Button


    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)


        val gson = Gson()
        val json: String? = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString(
            "user",
            "")
        val user: User = gson.fromJson(json, User::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewUserDorms)
        val adapter = DormListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.submitList(user.idUser?.let { dormViewModel.dormForUser(it) })

        imagview = findViewById(R.id.iv_camera)
        btnCapture= findViewById(R.id.btnCapture)



        btnCapture.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
            startActivityForResult(intent, 1000)
        }


        findViewById<TextView>(R.id.txtvProfileName).text = "Username: " +user.username
        findViewById<TextView>(R.id.txtvProfileEmail).text = "Email: " +user.email
        findViewById<TextView>(R.id.txtvProfilePhone).text = "Phone: " + user.phoneNr

        if(user.role.equals("Renter")) {
            findViewById<TextView>(R.id.txtvMyDorms).visibility = View.GONE
        }

        if(user.pic != null){
                val bitmap = BitmapFactory.decodeByteArray(user.pic, 0, user.pic.size)

                if(bitmap != null ){
                    imagview.setImageBitmap(bitmap);
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val gson = Gson()
        val json: String? = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString(
            "user",
            "")
        val user: User = gson.fromJson(json, User::class.java)

        val userViewModel: UserViewModel by viewModels {
            UserViewModelFactory((application as Application).repositoryUser)
        }


        var bitmap: Bitmap

        if(requestCode == 1000 && resultCode == RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
            imagview.setImageBitmap(bitmap)

            bitmap = (findViewById<ImageView>(R.id.iv_camera).drawable as BitmapDrawable).bitmap as Bitmap
            Log.d(" bitmap of imagview", bitmap.toString())

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()

            updatePic(image, user, userViewModel)


        }
    }

    private fun updatePic(pic: ByteArray, user: User, userViewModel: UserViewModel){

        var updatedPic = User(user.idUser,
            user.username,
            user.password,
            user.role,
            user.email,
            user.phoneNr,
            pic)
        Log.d("update User his Pic", updatedPic.toString())
        userViewModel.updateUser(updatedPic)

        val prefsEditor: SharedPreferences.Editor = android.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
        prefsEditor.remove("user")
        prefsEditor.commit()
        val gson = Gson()
        val json = gson.toJson(updatedPic)
        prefsEditor.putString("user", json)
        prefsEditor.commit()



        val bitmap = updatedPic.pic?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
        imagview.setImageBitmap(bitmap);



    }
}
