package com.example.mapsapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mapsapi.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : OnMapReadyCallback, AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyBinding()
    }

    private fun applyBinding() {
        val mapFrag: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.maps_fragment) as SupportMapFragment
        mapFrag.getMapAsync(this)

        binding.buttonSydney.setOnClickListener {
            map.clear()
            val sydney = LatLng((-34).toDouble(), (151).toDouble())
            map.addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Sydney Test")
            )
            map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
        binding.allStations.setOnClickListener {
            map.clear()

            val latBound = LatLngBounds.builder()
            for (i in TestLocations.data) {
                latBound.include(i.dest)
                map.addMarker(
                    MarkerOptions()
                        .position(i.dest)
                        .title(i.name)
                )
            }
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(latBound.build(), 300))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}