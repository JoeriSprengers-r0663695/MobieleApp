package com.example.mobieleapp.data.database.user

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.*

class UserActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val button = findViewById<Button>(R.id.BevestigId)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (findViewById<EditText>(R.id.userId).text.isEmpty() || findViewById<EditText>(R.id.userId).text.isEmpty() || findViewById<EditText>(R.id.userId).text.isEmpty()){

                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {

                val username = findViewById<EditText>(R.id.userId).text.toString()

                val passwoord = findViewById<EditText>(R.id.passwoordId).text.toString()

                val role = findViewById<EditText>(R.id.roleId).text.toString()

                userViewModel.allUsers.observe(this) {user ->
                    Log.d("users",user[1].toString())
                }
                val user = User(null,username, passwoord, role)
                userViewModel.insert( user)
                finish()
            }
        }
    }
}
