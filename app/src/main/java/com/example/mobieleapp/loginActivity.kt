package com.example.mobieleapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.dorm.DormListActivity
import com.example.mobieleapp.data.database.dorm.DormViewModelFactory
import com.example.mobieleapp.data.database.user.User
import com.example.mobieleapp.data.database.dorm.DormViewModel
import com.example.mobieleapp.data.database.user.RegisterActivity
import com.example.mobieleapp.data.database.user.UserViewModel
import com.example.mobieleapp.data.database.user.UserViewModelFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*


class loginActivity : AppCompatActivity() {

   private val currentUser : User? = null

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.btn_Login)
        val usernameEditText = findViewById<EditText>(R.id.username2).text
        val passwordEditText = findViewById<EditText>(R.id.password2).text

        loginButton.setOnClickListener {

            if(usernameEditText.isEmpty() && passwordEditText.isEmpty()){
                Toast.makeText(
                    applicationContext,
                    "Empty, fill something in",
                    Toast.LENGTH_LONG
                ).show()
            }else{

                var found = false
                userViewModel.allUsers?.observe(this){ users -> for(i in users){
                        i.username.toString().equals(usernameEditText.toString())

                        if (i.username.toString().equals(usernameEditText.toString())&& i.password.toString().equals(passwordEditText.toString())){
                            found = true
                            Toast.makeText(
                                applicationContext,
                                "Welcome " + i.username,
                                Toast.LENGTH_LONG
                            ).show()

                            val intent = Intent(this, DormListActivity::class.java)

                            val prefsEditor: SharedPreferences.Editor = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
                            val gson = Gson()
                            val json = gson.toJson(i)
                            prefsEditor.putString("user", json)

                            prefsEditor.commit()
                            finish()
                            startActivity(intent)
                            break
                        }
                }
                    if(!found) {
                        Toast.makeText(
                            applicationContext,
                            R.string.invalid_username,
                            Toast.LENGTH_SHORT

                        ).show()
                    }
                }
            }
        }

        btn_registerNow.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}