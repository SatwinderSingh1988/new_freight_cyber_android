package com.app.freightCyber.presentation.home_dashboard.job.active_job


import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentActiveJobMapBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.showInfoToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActiveJobMapFragment : BaseFragment<FragmentActiveJobMapBinding>(), OnMapReadyCallback {

    private var data: String? = null
    private val viewModel: ActiveJobMapFragmentVM by viewModels()
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1


    override fun getLayoutResource(): Int {
        return R.layout.fragment_active_job_map
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mapFragment?.getMapAsync(this)
        getData()
        bottomSheetBehaviour()
    }

    private fun getData() {
        data = arguments?.getString("FROM")
        if(data == "FROM_SEARCH"){
            binding.tvMarkEnroute.text = "Pick-up Items Declarations"
        }else{
            binding.tvMarkEnroute.text = "Start Route"
        }
    }

    private fun bottomSheetBehaviour() {
        val standardBottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.standardBottomSheet)
        standardBottomSheetBehavior.peekHeight = 160
        standardBottomSheetBehavior.isHideable = false
        standardBottomSheetBehavior.isDraggable = true
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        binding.bottomSheet.tvReport.setOnClickListener {
            showInfoToast("clicked")
        }
        binding.bottomSheet.tvSafetyInductionRequired.setOnClickListener {
           HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.safetyInductionFragment)
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }

                R.id.tvMarkEnroute -> {
                    if(data=="FROM_SEARCH"){
                        HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.pickupItemFragment)
                    }else{
                        HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.routeMapFragment)
                    }
                }
            }
        })
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        /*p0.setOnMapClickListener { latLng ->
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
            p0.clear()
            p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            p0.addMarker(markerOptions)
        }*/
        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = false
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val currentLocation = LatLng(it.latitude  , it.longitude)
                    val currentLocationDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.current_location_icon)
                    val currentLocationBitmap = currentLocationDrawable?.toBitmap()
                    mMap.addMarker(MarkerOptions()
                        .position(currentLocation)
                        .title("You are here")
                        .icon(BitmapDescriptorFactory.fromBitmap(currentLocationBitmap!!))
                    )
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10f))
                }
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}