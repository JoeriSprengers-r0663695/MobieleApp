package com.example.mobieleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavDeepLinkBuilder
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.dorm.DormListActivity
import com.example.mobieleapp.data.database.user.UserActivity
import com.example.mobieleapp.data.database.user.UserViewModel
import com.example.mobieleapp.data.database.user.UserViewModelFactory
import com.example.mobieleapp.data.database.wordbrol.WordActivity
import kotlinx.android.synthetic.main.activity_login.*

class loginActivity : AppCompatActivity() {


    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.btn_Login)
        var found :Boolean = false
        val usernameEditText = findViewById<EditText>(R.id.username2).text
        val passwordEditText = findViewById<EditText>(R.id.password2).text

        loginButton.setOnClickListener {
            Log.d("userinfo", usernameEditText.toString())
            Log.d("userinfo", passwordEditText.toString())


            /* loadingProgressBar.visibility = View.VISIBLE
             loginViewModel.login(
                 usernameEditText.text.toString(),
                 passwordEditText.text.toString()
             )*/
            if(usernameEditText.isEmpty() && passwordEditText.isEmpty()){
                Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }else{


                Log.d("viewmodel", userViewModel.toString())
                userViewModel.allUsers?.observe(this){ users ->
                    for(i in users){

                        /*Log.d("observerUser", i.username.toString())
                        Log.d("observerUserInTextEdit", usernameEditText.toString())
                        Log.d("observerUser", i.password.toString())
                        Log.d("observerUserInTextEdit", passwordEditText.toString())
                        Log.d("checkEquealsUsername",  i.username.toString().equals(usernameEditText.toString()).toString())*/
                        i.username.toString().equals(usernameEditText.toString())

                        if (i.username.toString().equals(usernameEditText.toString())&& i.password.toString().equals(passwordEditText.toString())){
                          /*  Log.d("observerUser", usernameEditText.toString())
                            Log.d("observerUser", i.username.toString())
                            Log.d("observerUser", passwordEditText.toString())
                            Log.d("observerUser", i.password.toString())*/
                            Log.d("observerUser", i.role.toString())
                            found = true


                            val intent = Intent(this, DormListActivity::class.java)
                            startActivity(intent)

                        }
                    }
                }

                if(!found) {
                    Toast.makeText(
                        applicationContext,
                        R.string.invalid_username,
                        Toast.LENGTH_LONG

                    ).show()
                }
                if(found){
                    found = false
                }
            }
        }

        btn_registerNow.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }
    }
}