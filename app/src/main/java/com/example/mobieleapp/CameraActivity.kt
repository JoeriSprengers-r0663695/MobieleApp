package com.example.mobieleapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.dorm.DormListActivity
import com.example.mobieleapp.data.database.dorm.DormListAdapter
import com.example.mobieleapp.data.database.dorm.DormViewModel
import com.example.mobieleapp.data.database.dorm.DormViewModelFactory
import com.example.mobieleapp.data.database.user.UpdateProfilePicActivity
import com.example.mobieleapp.data.database.user.User
import com.example.mobieleapp.data.database.user.UserViewModel
import com.example.mobieleapp.data.database.user.UserViewModelFactory
import com.google.gson.Gson
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream

class CameraActivity : AppCompatActivity() {
    lateinit var imagview : ImageView
    lateinit var btnCapture: Button
    lateinit var btnSafeProfile: Button
    private lateinit var bitmap : Bitmap


    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {

        val userViewModel: UserViewModel by viewModels {
            UserViewModelFactory((application as Application).repositoryUser)
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)


        val gson = Gson()
        val json: String? = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("user", "")
        val user: User = gson.fromJson(json, User::class.java)

        Log.d("user in current session",user.username.toString())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewUserDorms)
        val adapter = DormListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.submitList(user.idUser?.let { dormViewModel.dormForUser(it) })

        imagview = findViewById(R.id.iv_camera)
        btnCapture= findViewById(R.id.btnCapture)
        btnSafeProfile = findViewById(R.id.btnSafeProfile)



        btnCapture.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
            startActivityForResult(intent,1000)
        }
        btnSafeProfile.setOnClickListener{
           bitmap = (findViewById<ImageView>(R.id.iv_camera).drawable as BitmapDrawable).bitmap as Bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()

            updatePic(image, user, userViewModel)
        }

        findViewById<TextView>(R.id.txtvProfileName).text = user.username
        findViewById<TextView>(R.id.txtvProfileEmail).text = user.email
        findViewById<TextView>(R.id.txtvProfilePhone).text = user.phoneNr


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var bitmap: Bitmap
        var intent: Intent
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000 && resultCode == RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
            imagview.setImageBitmap(bitmap)
        }
        if(requestCode == 2000 && resultCode == RESULT_OK){


        }
    }

    private fun updatePic(pic: ByteArray, user: User, userViewModel: UserViewModel): User?{

        var updatedPic = User(user.idUser, user.username,user.password,user.role,user.email,user.phoneNr,pic)
        Log.d("update User his Pic", updatedPic.toString())
        userViewModel.updateUser(updatedPic)
        return updatedPic
    }
}
