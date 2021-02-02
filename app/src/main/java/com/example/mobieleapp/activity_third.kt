package com.example.mobieleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class activity_third : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        val button = findViewById<Button>(R.id.button3)
        button.setOnClickListener{
            finish()
        }
    }
}