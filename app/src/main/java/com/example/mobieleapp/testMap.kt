package com.example.mobieleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myfirstapp.MainViewModel
import com.example.myfirstapp.MainViewModelFactory
import com.example.myfirstapp.repository.Repository

class testMap : Fragment() {


    //initializations and declarations for the Geocoding part
    private lateinit var viewModel: MainViewModel
    private val repository = Repository()
    private val viewModelFactory = MainViewModelFactory(repository)
    private var lat : String = ""
    private var long : String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //static adres line - temporary
        view.findViewById<TextView>(R.id.adres).text = "Nieuwpoortsesteenweg 467 Oostende "


        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost(view.findViewById<TextView>(R.id.adres).text.toString())
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->

            //assigning response data value to global vars for later use
            lat = response.body()?.data?.get(0)?.latitude.toString()
            long = response.body()?.data?.get(0)?.longitude.toString()

        })


        view.findViewById<Button>(R.id.MapId).setOnClickListener {

            val action = testMapDirections.actionTestMap2ToMapsFragment(lat, long)
            findNavController().navigate(action)
        }
    }
}