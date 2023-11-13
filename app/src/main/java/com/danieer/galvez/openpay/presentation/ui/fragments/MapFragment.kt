package com.danieer.galvez.openpay.presentation.ui.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.danieer.galvez.openpay.databinding.FragmentMapBinding
import com.danieer.galvez.openpay.presentation.di.factory.ViewModelFactory
import com.danieer.galvez.openpay.presentation.ui.viewmodel.MapFragmentViewModel
import com.danieer.galvez.openpay.utils.DeviceIdHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var gMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MapFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MapFragmentViewModel::class.java]

        binding.mapView.apply {
            onCreate(savedInstanceState)
        }
        verifyLocationPermission()
    }

    private fun verifyLocationPermission() {
        if (checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PermissionChecker.PERMISSION_GRANTED && checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PermissionChecker.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            binding.mapView.getMapAsync(this)
            enableLocation()
        }

    }

    override fun onMapReady(p0: GoogleMap) {
        gMap = p0
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            gMap.isMyLocationEnabled = true
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    binding.mapView.getMapAsync(this@MapFragment)
                    enableLocation()
                }
            }
        }
    }

    private fun enableLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0.lastLocation?.let {
                    updateMapLocation(it.latitude, it.longitude)

                }
            }
        }
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 500000
        }


        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
        }


    }

    private fun updateMapLocation(latitude: Double, longitude: Double) {
        val currentLatLng = LatLng(latitude, longitude)
        gMap.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title("Your Location"))
        gMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng))
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        saveLocationToFirestore(latitude, longitude)
    }

    private fun saveLocationToFirestore(latitude: Double, longitude: Double) {
        val deviceId = DeviceIdHelper.getDeviceId(requireContext())
        viewModel.saveLocationToFirestore(
            latitude, longitude, deviceId
        ).addOnSuccessListener {
            Toast.makeText(requireContext(), "Location saved successfully", Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Location cannot be saved", Toast.LENGTH_LONG)
        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}