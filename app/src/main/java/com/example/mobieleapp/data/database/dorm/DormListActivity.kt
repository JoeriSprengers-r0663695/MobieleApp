package com.example.mobieleapp.data.database.dorm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.CameraActivity
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.user.User
import com.example.mobieleapp.data.database.user.UserViewModel
import com.example.mobieleapp.data.database.user.UserViewModelFactory

class DormListActivity : AppCompatActivity() {


    private val newWordActivityRequestCode = 1
    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)




        var user  = intent.getSerializableExtra("user") as User
            Log.d("user",user.toString())

        if(user.role.toString() == "Renter") {
            findViewById<Button>(R.id.addKotId).visibility = View.GONE
        }


        var userTest = userViewModel.getById(0)
        Log.d("querrie", userTest.toString())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewDorms)
        val adapter = DormListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        dormViewModel.allDorms.observe(owner =  this) { dorms ->
            // Update the cached copy of the dorms in the adapter// .
            //Log.d("woord",dorms.toString() )

            dorms.let { adapter.submitList(it) }

        }


        findViewById<Button>(R.id.addKotId).setOnClickListener {
            val intent = Intent(this@DormListActivity, DormActivity::class.java).putExtra("user",user)
            startActivity(intent)
            finish()

        }
        findViewById<Button>(R.id.btn_toProfile).setOnClickListener {
            val intent = Intent(this@DormListActivity, CameraActivity::class.java).putExtra("user",user)
            startActivity(intent)
            finish()

        }

    }
}