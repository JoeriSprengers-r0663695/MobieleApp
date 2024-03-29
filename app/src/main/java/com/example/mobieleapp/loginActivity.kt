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

    private val currentUser: User? = null

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var database = FirebaseDatabase.getInstance().reference.child("User")

        val loginButton = findViewById<Button>(R.id.btn_Login)
        val emailEditText = findViewById<EditText>(R.id.email).text
        val passwordEditText = findViewById<EditText>(R.id.password).text

        loginButton.setOnClickListener {

            if (emailEditText.isEmpty() && passwordEditText.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Empty, fill something in",
                    Toast.LENGTH_LONG
                ).show()
            } else {


                Log.d("data out textbox", emailEditText.toString())
                var found = false

                userViewModel.allUsers?.observe(this) { users ->
                    for (i in users) {
                        var user: HashMap<String, String>
                        var getdata = object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val usernameEditText = findViewById<EditText>(R.id.email).text
                                val passwordEditText = findViewById<EditText>(R.id.password).text

                                if (snapshot.exists()) {
                                    Log.d("data out textbox in cha", usernameEditText.toString())

                                    for (i in snapshot.children) {
                                        //Log.d("gevonden key+val",i.toString())

                                        user = i.value as HashMap<String, String>
                                        Log.d("gevonden pass", user.get("password").toString())
                                        Log.d("gevonden username", user.get("email").toString())
                                        if (user.get("email").toString().equals(usernameEditText.toString()) && user.get("password").toString().equals(passwordEditText.toString())) {
                                            Log.d("------------------",
                                                user.get("password").toString())
                                            found = true
                                            Toast.makeText(
                                                applicationContext,
                                                "Welcome " + user.get("username"),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            var arr: String = ""
                                            if (user.get("pic").toString() != "") {
                                                arr = user.get("pic").toString()
                                            }
                                            val user = UserFireBase(user.get("username").toString(),
                                                user.get("password").toString(),
                                                user.get("role").toString(),
                                                user.get("email").toString(),
                                                user.get("phoneNr").toString(),
                                                null)

                                            val intent = Intent(this@loginActivity,DormListActivity::class.java)

                                            val prefsEditor: SharedPreferences.Editor =
                                                PreferenceManager.getDefaultSharedPreferences(
                                                    applicationContext).edit()
                                            val gson = Gson()
                                            val json = gson.toJson(user)
                                            prefsEditor.putString("user", json)

                                            prefsEditor.commit()
                                            finish()
                                            Log.d("2de stuck", "zit aan tweede intent")
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
                        database.addValueEventListener(getdata)

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