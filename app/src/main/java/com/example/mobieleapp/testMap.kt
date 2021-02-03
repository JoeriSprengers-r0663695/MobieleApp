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


    private lateinit var viewModel: MainViewModel
    private val repository = Repository()
    private val viewModelFactory = MainViewModelFactory(repository)




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //adres line
        view.findViewById<TextView>(R.id.adres).text = "Nieuwpoortsesteenweg Oostende 467"


        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost(view.findViewById<TextView>(R.id.adres).text.toString())
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            //Showing Lat en Long in textview onscreen
            view.findViewById<TextView>(R.id.lat).text= response.body()?.data?.get(0)?.latitude.toString()
            view.findViewById<TextView>(R.id.longi).text = response.body()?.data?.get(0)?.longitude.toString()
        })



        view.findViewById<Button>(R.id.MapId).setOnClickListener {

            val currentLat =  view.findViewById<TextView>(R.id.lat).text.toString()
            val currentLong = view.findViewById<TextView>(R.id.longi).text.toString()
            val action = testMapDirections.actionTestMap2ToMapsFragment(currentLat, currentLong)


            findNavController().navigate(action)
        }
    }
}