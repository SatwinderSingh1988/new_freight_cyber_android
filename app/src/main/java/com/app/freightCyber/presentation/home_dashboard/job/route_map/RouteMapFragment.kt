package com.app.freightCyber.presentation.home_dashboard.job.route_map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.MapSearchData
import com.app.freightCyber.databinding.FragmentRouteMapBinding
import com.app.freightCyber.databinding.SearchBotomSheetListItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.presentation.test_route_api.DirectionRespponse
import com.app.freightCyber.presentation.test_route_api.RouteInterface
import com.app.freightCyber.utils.HelperUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.PolyUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class RouteMapFragment : BaseFragment<FragmentRouteMapBinding>(), OnMapReadyCallback {

    private val viewModel: RouteMapFragmentVM by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val originLatitude = 30.704758882995268
    private val originLongitude = 76.71783197351996
    private val destinationLatitude = 30.73364006706702
    private val destinationLongitude = 76.7794028644081
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private lateinit var mMap: GoogleMap

    override fun getLayoutResource(): Int {
        return R.layout.fragment_route_map
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        bottomSheetBehaviour()
        setUpSearchAdapter()

}

    private var searchAdapter : SimpleRecyclerViewAdapter<MapSearchData, SearchBotomSheetListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpSearchAdapter() {
        searchAdapter =
            SimpleRecyclerViewAdapter(R.layout.search_botom_sheet_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {

                    }
                })
        binding.bottomSheet.rvSearch.adapter = searchAdapter
        searchAdapter?.list = searchList()
    }

    private fun searchList() : ArrayList<MapSearchData>{
        val list = ArrayList<MapSearchData>()
        list.add(MapSearchData(R.drawable.fuel ,"Fuel Stops"))
        list.add(MapSearchData(R.drawable.hotel ,"Hotels"))
        list.add(MapSearchData(R.drawable.atm ,"ATMs"))
        list.add(MapSearchData(R.drawable.toll_bot ,"Toll Booths"))
        list.add(MapSearchData(R.drawable.truck_service ,"Truck Service"))
        list.add(MapSearchData(R.drawable.restauronts ,"Restaurants"))
        return list
    }


    private fun bottomSheetBehaviour() {
        val standardBottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.standardBottomSheet)
        standardBottomSheetBehavior.peekHeight = 280
        standardBottomSheetBehavior.isHideable = false
        standardBottomSheetBehavior.isDraggable = false
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        binding.bottomSheet.imgSearch.setOnClickListener{
            binding.bottomSheet.showRouteBottomSheet.visibility = View.GONE
            binding.bottomSheet.searchBottomSheet.visibility = View.VISIBLE
            showSoftKeyboard(binding.bottomSheet.etSearch)
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.bottomSheet.imgBack.setOnClickListener{
            binding.bottomSheet.showRouteBottomSheet.visibility = View.VISIBLE
            binding.bottomSheet.searchBottomSheet.visibility = View.GONE
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.bottomSheet.imgClose.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("FROM" , "FROM_SEARCH")
            HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.activeJobMapFragment ,bundle)
        }
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = requireActivity().getSystemService(InputMethodManager::class.java)
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.tvNext -> {

                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val startPoint = LatLng(originLatitude, originLongitude)
        val endPoint = LatLng(destinationLatitude, destinationLongitude)

        val endDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.end_syymbol)
        val endBitmap = endDrawable?.toBitmap()

        mMap.addMarker(MarkerOptions().position(endPoint).title("End Point").icon(
            BitmapDescriptorFactory.fromBitmap(endBitmap!!)))

        val startDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.start_symbol)
        val startBitmap = startDrawable?.toBitmap()

        mMap.addMarker(MarkerOptions().position(startPoint).title("Start Point").icon(
            BitmapDescriptorFactory.fromBitmap(startBitmap!!)))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 12f))
        mMap.uiSettings.isZoomControlsEnabled = true

        getRoute(startPoint, endPoint)
        enableMyLocation()
    }

    private fun getRoute(startPoint: LatLng, endPoint: LatLng) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val service = retrofit.create(RouteInterface::class.java)
        val call = service.getDirections(
            "${startPoint.latitude},${startPoint.longitude}",
            "${endPoint.latitude},${endPoint.longitude}",
            "AIzaSyD5Jt2e9ocVmXovnsOsdmtdhPRkP8m9IhQ"
        )

        call.enqueue(object : Callback<DirectionRespponse> {
            override fun onResponse(call: Call<DirectionRespponse>, response: Response<DirectionRespponse>) {
                val directionsResponse = response.body()
                if (directionsResponse != null) {
                    val points = directionsResponse.routes[0].overviewPolyline.points
                    val decodedPath = PolyUtil.decode(points)
                    mMap.addPolyline(
                        PolylineOptions().addAll(decodedPath)
                            .width(10f)
                            .color(ContextCompat.getColor(requireContext(), R.color.yellow))
                    )
                }
            }

            override fun onFailure(call: Call<DirectionRespponse>, t: Throwable) {
                // Handle the error
            }
        })
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