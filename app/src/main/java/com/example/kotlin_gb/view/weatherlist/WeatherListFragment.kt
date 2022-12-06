package com.example.kotlin_gb.view.weatherlist

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener

import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentWeatherListBinding
import com.example.kotlin_gb.room.model.City
import com.example.kotlin_gb.room.model.Weather
import com.example.kotlin_gb.utils.Const.Companion.LIST_OF_CITIES_KEY
import com.example.kotlin_gb.utils.Const.Companion.RUSSIAN_KEY
import com.example.kotlin_gb.utils.Const.Companion.WORLD_KEY
import com.example.kotlin_gb.utils.Utils.hideKeyboard
import com.example.kotlin_gb.utils.Utils.showSnackError
import com.example.kotlin_gb.utils.Utils.showSnackErrorWithAction
import com.example.kotlin_gb.view.details.OnCityClickable
import com.example.kotlin_gb.view.details.WeatherDetailsFragment
import com.example.kotlin_gb.viewmodel.AppState
import com.example.kotlin_gb.viewmodel.WeatherListViewModel
import com.google.android.material.snackbar.Snackbar


class WeatherListFragment : Fragment() {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    private lateinit var viewModel: WeatherListViewModel

    private lateinit var citiesAreaKey: String

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        citiesAreaKey = requireActivity().getPreferences(Context.MODE_PRIVATE)
            .getString(LIST_OF_CITIES_KEY, RUSSIAN_KEY)
            .toString()

        viewModel = ViewModelProvider(this)[WeatherListViewModel::class.java]

        viewModel.getLiveData().observe(viewLifecycleOwner) { it -> renderData(it) }

        showWeatherListAndIcon(citiesAreaKey)

        binding.fabSelectCitiesRegion.setOnClickListener {
            when (citiesAreaKey) {
                RUSSIAN_KEY -> {
                    citiesAreaKey = WORLD_KEY
                    showWeatherListAndIcon(citiesAreaKey)
                }
                WORLD_KEY -> {
                    citiesAreaKey = RUSSIAN_KEY
                    showWeatherListAndIcon(citiesAreaKey)
                }
            }
        }

        binding.fabFindGeoLocation.setOnClickListener {
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        binding.btnFindWithCoordinates.setOnClickListener {
            val lat = binding.etMyLatitude.text.toString()
            val lon = binding.etMyLongitude.text.toString()
            if (validateInputs(lat, lon)) {
                val latChecked = lat.trim().toDouble()
                val lonChecked = lon.trim().toDouble()
                //TODO round values of lat and lon
                val weather =
                    Weather(City("By coordinates", latChecked, lonChecked, "Unknown place"))

                requireActivity().supportFragmentManager.beginTransaction()
                    .hide(this@WeatherListFragment)
                    .add(R.id.container, WeatherDetailsFragment.newInstance(weather))
                    .addToBackStack("")
                    .commit()
                view.hideKeyboard()

            } else {
                view.hideKeyboard()
                binding.root.showSnackError("Input correct numbers", Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun checkPermission(accessFineLocation: String) {

        val permResult = ContextCompat.checkSelfPermission(requireContext(), accessFineLocation)

        //Проверяем случай, если разрешение на доступ к контактам уже дано.
        if (permResult == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        }
        //Опционально: если во второй раз (так же??)
        else if (shouldShowRequestPermissionRationale(accessFineLocation)) {
            showRationaleDialog(accessFineLocation)
        } else {
            permissionRequest(accessFineLocation)
        }
    }

    private fun showRationaleDialog(accessFineLocation: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Access to Location")
            .setMessage("Explanation, Explanation, Explanation")
            .setPositiveButton("Grant Access") { _, _ ->
                permissionRequest(accessFineLocation)
            }
            .setNegativeButton("Deny Access") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private val REQUEST_CODE_LOCATION = 999

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_LOCATION)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            for (pIndex in permissions.indices) {
                if (permissions[pIndex] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[pIndex] == PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation()
                    Log.d("TAG", "Im HERE!!!")
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getLocation() {
        // Еще раз проверяем РАЗРЕШЕНИЯ: ACCESS_COARSE_LOCATION + ACCESS_FINE_LOCATION
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000L,
                    1F,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            Log.d("TAG", "location = ${location.latitude}, ${location.longitude}")
                        }
                    })
            }
        }
    }

    private fun showWeatherListAndIcon(citiesArea: String) {
        when (citiesArea) {
            RUSSIAN_KEY -> {
                viewModel.getRussianList()
                binding.fabSelectCitiesRegion
                    .setImageResource(R.drawable.flag_russia)
            }
            WORLD_KEY -> {
                viewModel.getWorldList()
                binding.fabSelectCitiesRegion
                    .setImageResource(R.drawable.flag_world)
            }
        }
    }

    private fun validateInputs(lat: String, lon: String): Boolean {
        //TODO check input values for correct
        return (lat.isNotEmpty() && lon.isNotEmpty())
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.showError()
                binding.root.showSnackErrorWithAction(
                    getString(R.string.snack_bar_error_title),
                    Snackbar.LENGTH_INDEFINITE,
                    getString(R.string.snack_bar_reload_title),
                ) { showWeatherListAndIcon(citiesAreaKey) }
            }
            is AppState.Loading -> {
                binding.loading()
            }
            is AppState.SuccessSingleWeather -> {
                binding.showResult()
            }
            is AppState.SuccessMultiWeather -> {
                binding.showResult()
                val result = appState.weatherListData
                val rv = binding.rvWeatherList
                rv.layoutManager = LinearLayoutManager(requireActivity())

                rv.adapter = WeatherListAdapter(result, object : OnCityClickable {
                    override fun onCityClick(weather: Weather) {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .hide(this@WeatherListFragment)
                            .add(R.id.container, WeatherDetailsFragment.newInstance(weather))
                            .addToBackStack("")
                            .commit()
                        view?.hideKeyboard()
                    }
                })
            }
        }
    }

    // Функция-расширение
    private fun FragmentWeatherListBinding.loading() {
        this.flLoadingLayout.visibility = View.VISIBLE
        this.fabSelectCitiesRegion.visibility = View.GONE
    }

    // Функция-расширение
    private fun FragmentWeatherListBinding.showResult() {
        this.flLoadingLayout.visibility = View.GONE
        this.fabSelectCitiesRegion.visibility = View.VISIBLE
    }

    // Функция-расширение
    private fun FragmentWeatherListBinding.showError() {
        this.flLoadingLayout.visibility = View.GONE
        this.fabSelectCitiesRegion.visibility = View.GONE
    }

    override fun onStop() {
        requireActivity().getPreferences(Context.MODE_PRIVATE).edit()
            .putString(LIST_OF_CITIES_KEY, citiesAreaKey).apply()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}