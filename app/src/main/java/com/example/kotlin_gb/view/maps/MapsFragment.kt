package com.example.kotlin_gb.view.maps

import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentMapsUiBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        // Рисовать линии между маркерами на карте:
        googleMap.setOnMapLongClickListener {
            addMarkerToArray(it)
            setMarker(it, "", R.drawable.ic_marker_google_map)
            drawLine()
        }


        // + - на карте:
        googleMap.uiSettings.isZoomControlsEnabled = true
        // найти себя:
        // TODO permission for this!!!
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
    }


    private var _binding: FragmentMapsUiBinding? = null
    private val binding: FragmentMapsUiBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsUiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.btnSearchAddress.setOnClickListener {
            binding.etSearchAddress.text.toString().let { searchAddress ->

                if (searchAddress.trim() != "") {
                    val geocoder = Geocoder(requireContext())
                    val address = geocoder.getFromLocationName(searchAddress, 1)
                    Log.d("TAG", "$address")
                    if (address != null && address.size != 0) {
                        val ln = LatLng(address.first().latitude, address.first().longitude)
                        setMarker(
                            ln,
                            searchAddress,
                            R.drawable.ic_marker_google_map
                        )
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ln, 10f))

                    } else {
                        // TODO ВЫНЕСТИ ВСЕ АЛЕРТЫ В Utils!!!!
                        AlertDialog.Builder(requireContext())
                            .setTitle("Searching your address")
                            .setMessage("UNKNOWN ADDRESS!!!")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }
                }
            }
        }
    }

    private fun setMarker(location: LatLng, searchText: String, resourceId: Int): Marker? {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.fromResource(resourceId))
        )
    }

    private val markers = mutableListOf<Marker>()
    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(), R.drawable.ic_marker_google_map)
        markers.add(marker!!)
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.RED)
                    .width(10f)
            )
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}