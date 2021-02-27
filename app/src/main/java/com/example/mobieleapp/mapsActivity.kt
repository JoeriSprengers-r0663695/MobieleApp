package com.example.mobieleapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class mapsActivity : AppCompatActivity() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        //Putting the passed through lat and long in variables
        var long  = intent.getSerializableExtra("long") as String
        var lat  = intent.getSerializableExtra("lat") as String

        //Debug stuff
        Log.d("longitude values",long )
        Log.d("Lat values",lat )

        val customHome = LatLng(lat.toDouble(),long.toDouble())
        val belgium = LatLng(50.85045, 4.34878)
        googleMap.addMarker(MarkerOptions().position(customHome).title("Marker for Custom"))
        googleMap.addMarker(MarkerOptions().position(belgium).title("Marker in Belgium"))

        googleMap.moveCamera(CameraUpdateFactory.zoomTo(7.0f))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(customHome))
    }



 //   @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_maps)
//      setSupportActionBar(findViewById(R.id.toolbar))
//        Toolbar(findViewById(R.id.toolbar))

        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
}