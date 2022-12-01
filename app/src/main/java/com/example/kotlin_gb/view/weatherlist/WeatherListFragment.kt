package com.example.kotlin_gb.view.weatherlist

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentWeatherListBinding
import com.example.kotlin_gb.model.City
import com.example.kotlin_gb.model.Weather
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

        binding.fab.setOnClickListener {
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

    private fun showWeatherListAndIcon(citiesArea: String) {
        when (citiesArea) {
            RUSSIAN_KEY -> {
                viewModel.getRussianList()
                binding.fab
                    .setImageResource(R.drawable.flag_russia)
            }
            WORLD_KEY -> {
                viewModel.getWorldList()
                binding.fab
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
        this.fab.visibility = View.GONE
    }

    // Функция-расширение
    private fun FragmentWeatherListBinding.showResult() {
        this.flLoadingLayout.visibility = View.GONE
        this.fab.visibility = View.VISIBLE
    }

    // Функция-расширение
    private fun FragmentWeatherListBinding.showError() {
        this.flLoadingLayout.visibility = View.GONE
        this.fab.visibility = View.GONE
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