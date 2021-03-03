package com.example.mobieleapp

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.mobieleapp.data.database.user.User
import com.google.gson.Gson
import org.w3c.dom.Text

class CameraActivity : AppCompatActivity() {
    lateinit var imagview : ImageView
    lateinit var btnCapture: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)


        val gson = Gson()
        val json: String? = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("user", "")
        val u: User = gson.fromJson(json, User::class.java)

        Log.d("user in current session",u.username.toString())

        var user  = intent.getSerializableExtra("user") as User

        imagview = findViewById(R.id.iv_camera)
        btnCapture= findViewById(R.id.btnCapture)

        btnCapture.setOnClickListener{
            intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,1000)
        }

        findViewById<TextView>(R.id.txtvProfileName).text = user.username
        findViewById<TextView>(R.id.txtvProfileEmail).text = user.email
        findViewById<TextView>(R.id.txtvProfilePhone).text = user.phoneNr
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var bitmap: Bitmap
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000 && resultCode == RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
            imagview.setImageBitmap(bitmap)
        }
    }
}
