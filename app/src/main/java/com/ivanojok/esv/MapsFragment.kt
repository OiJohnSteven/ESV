package com.ivanojok.esv

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase
import com.ivanojok.esv.model.Case
import com.ivanojok.esv.model.PrefsManager

class MapsFragment : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    var locationPermissionGranted: Boolean = false
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2000
    lateinit var map : GoogleMap

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var lastKnownLocation: Location ?= null

    private val prefsManager = PrefsManager.INSTANCE



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_maps)

        val Id = prefsManager.getVictim().phone

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        val skip = findViewById<Button>(R.id.skip)
        val send = findViewById<Button>(R.id.send)
        val add = findViewById<TextView>(R.id.add)

        skip.setOnClickListener {
            startActivity(Intent(this, Services::class.java))
        }

        send.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("/cases").push()
            val k = ref.key

            if (lastKnownLocation != null){
                ref.setValue(Case(k!!, "Unidentified", lastKnownLocation!!.latitude.toString(), lastKnownLocation!!.longitude.toString(), Id!!))
            }
            else{
                Toast.makeText(this, "Unknown location", Toast.LENGTH_SHORT).show()
            }

        }

        add.setOnClickListener {
            var lat:String = ""
            var long = ""
            if (lastKnownLocation != null){
                lat = lastKnownLocation!!.latitude.toString()
               long = lastKnownLocation!!.longitude.toString()
            }
            val intent = Intent(this, CreateCase::class.java)
            intent.putExtra("latitude", lat)
            intent.putExtra("longitude", long)
            startActivity(intent)
        }
    }


//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<String>,
//                                            grantResults: IntArray) {
//        locationPermissionGranted = false
//        when (requestCode) {
//            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty() &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    locationPermissionGranted = true
//                    theLocation()
//                } else {
//                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
//                }
//            }
//            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        }
//        //updateLocationUI()
//    }


    fun theLocation(){

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            // return
        }

        map.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { p0 ->
            if (p0 != null){
                lastKnownLocation = p0
                val currLatLng = LatLng(
                    p0.latitude,
                    p0.longitude
                )

                map.animateCamera( CameraUpdateFactory.newLatLngZoom(
                    currLatLng, 12f
                ))
                map.addMarker(MarkerOptions().position(currLatLng).title("Current Location captured"))
            }
            else{
                val currLatLng = LatLng(
                    0.335368,
                    36.667302
                )

                map.animateCamera( CameraUpdateFactory.newLatLngZoom(
                    currLatLng, 25f
                ))
                map.addMarker(MarkerOptions().position(currLatLng).title("Location captured"))
            }

        }

    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0

//        val sydney = LatLng(-34.0, 151.0)
//        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f))

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        theLocation()
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        return false
    }


}


//implementation 'com.google.android.ads.consent:consent-library:1.0.6
//Failed to transform places-2.6.0.aar (com.google.android.libraries.places:places:2.6.0) to match attributes {artifactType=android-compiled-dependencies-resources, org.gradle.category=library, org.gradle.libraryelements=jar, org.gradle.status=release, org.gradle.usage=java-runtime}.


//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