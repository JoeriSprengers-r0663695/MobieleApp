package com.example.mobieleapp.data.database.dorm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.CameraActivity
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.Application

class DormListActivity : AppCompatActivity() {


    private val newWordActivityRequestCode = 1
    private val dormViewModel: DormViewModel by viewModels {
        DormViewModelFactory((application as Application).repositoryDorm)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewDorms)
        val adapter = DormListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        dormViewModel.allDorms.observe(owner =  this) { dorms ->
            // Update the cached copy of the dorms in the adapter// .
            Log.d("woord",dorms.toString() )

            dorms.let { adapter.submitList(it) }

        }
        findViewById<Button>(R.id.btn_toProfile).setOnClickListener {
            val intent = Intent(this@DormListActivity, CameraActivity::class.java)
            startActivity(intent)
            finish()

        }

    }
}