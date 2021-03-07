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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class RegisterActivity : AppCompatActivity() {
    var maxId:Long = 0


    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var database = FirebaseDatabase.getInstance().reference


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


                var getdata = object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var sb = StringBuilder()
                        var list: ArrayList<Long> = ArrayList()
                        if(snapshot.exists()) {
                            var a = UserFireBase(maxId+1,username, passwoord, role, email, phoneNr,null)

                            for (i in snapshot.children) {

                                list.add(i.child("idUser").getValue() as Long)
                                maxId = list.size.toLong()
                                Log.d("List",list.toString())

                                Log.d("maxid",maxId.toString())
                            }
                            database.child(a.idUser.toString()).setValue(a)
                        }else{
                            maxId = 0
                        }
                    }


                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                }
                database.addValueEventListener(getdata)
                database.addListenerForSingleValueEvent(getdata)

                Log.d("GetMaxID", maxId.toString())


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
