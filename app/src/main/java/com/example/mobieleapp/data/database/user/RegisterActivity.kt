package com.example.mobieleapp.data.database.user

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.*
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    var maxId:Long = 0


    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var database = FirebaseDatabase.getInstance().reference.child("User")


        val button = findViewById<Button>(R.id.btn_BevestigId)
        button.setOnClickListener {
            val replyIntent = Intent()



            if (findViewById<EditText>(R.id.txtUsername).text.isEmpty() || findViewById<EditText>(R.id.txtPassword).text.isEmpty()){

                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {

                val username = findViewById<EditText>(R.id.txtUsername).text.toString()

                val passwoord = findViewById<EditText>(R.id.txtPassword).text.toString()

                val role = findViewById<ToggleButton>(R.id.tb_role).text.toString()

                val email = findViewById<EditText>(R.id.txtEmail).text.toString()

                val phoneNr = findViewById<EditText>(R.id.txtPhoneNr).text.toString()




                var a = UserFireBase(username, passwoord, role, email, phoneNr,null)

                a.username?.let { it1 -> database.child(it1).setValue(a) }

                userViewModel.allUsers.observe(this) {user ->
                    Log.d("users",user[1].toString())
                }
                var b = User(null,username, passwoord, role, email, phoneNr,null)
                userViewModel.insert( b)
                Log.d("getbyid", userViewModel.getById(0).toString())
                finish()
            }
        }
    }
}
