package com.example.mobieleapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.dorm.DormListActivity
import com.example.mobieleapp.data.database.user.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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


        var database = FirebaseDatabase.getInstance().reference.child("User")

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



                Log.d("data out textbox", usernameEditText.toString())
                var found = false

                userViewModel.allUsers?.observe(this){ users -> for(i in users){
                    var user:HashMap<String,String>
                    var getdata = object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val usernameEditText = findViewById<EditText>(R.id.username2).text
                            val passwordEditText = findViewById<EditText>(R.id.password2).text

                            if (snapshot.exists()) {
                                Log.d("data out textbox in cha", usernameEditText.toString())

                                for (i in snapshot.children) {
                                    //Log.d("gevonden key+val",i.toString())

                                    user = i.value as HashMap<String, String>
                                    Log.d("gevonden pass",user.get("password").toString())
                                    Log.d("gevonden username",user.get("username").toString())
                                    if(user.get("username").toString().equals(usernameEditText.toString()) && user.get("password").toString().equals(passwordEditText.toString())){
                                        Log.d("------------------",user.get("password").toString())
                                        found = true
                                        Toast.makeText(
                                            applicationContext,
                                            "Welcome " + user.get("username"),
                                            Toast.LENGTH_LONG
                                        ).show()
                                       var arr: String= ""
                                        if(user.get("pic").toString() != ""){
                                          arr = user.get("pic").toString()
                                        }
                                        val user = UserFireBase(user.get("username").toString(),user.get("password").toString(),user.get("role").toString(),user.get("email").toString(),user.get("phoneNr").toString(),null)

                                       Log.d("checkuseroutDb",user.pic.toString())
                                        val intent = Intent(this@loginActivity, DormListActivity::class.java)

                                        val prefsEditor: SharedPreferences.Editor = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
                                        val gson = Gson()
                                        val json = gson.toJson(user)
                                        prefsEditor.putString("user", json)

                                        prefsEditor.commit()
                                        finish()
                                        startActivity(intent)
                                        break

                                    }


                                }
                            }
                        }


                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    }





                   val value = database.addValueEventListener(getdata)
                    Log.d("valueofListener", value.toString())
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