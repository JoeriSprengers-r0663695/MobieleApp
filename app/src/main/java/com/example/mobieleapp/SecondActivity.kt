package com.example.mobieleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SecondActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)



        val buttonReturn = findViewById<Button>(R.id.button2)
        val buttonNext = findViewById<Button>(R.id.fab)
        buttonReturn.setOnClickListener{
            finish()
        }
        buttonNext.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

    }
}