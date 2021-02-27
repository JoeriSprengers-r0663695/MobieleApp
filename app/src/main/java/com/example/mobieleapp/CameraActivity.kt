package com.example.mobieleapp

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

class CameraActivity : AppCompatActivity() {
    lateinit var imagview : ImageView
    lateinit var btnCapture: Button

    override fun onCreate(savedInstanceState: Bundle?) {
       var intent : Intent
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        imagview = findViewById(R.id.iv_camera)
        btnCapture= findViewById(R.id.btnCapture)

        btnCapture.setOnClickListener{
            intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,1000)
        }


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
