package com.example.kotlin_gb.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_gb.databinding.FragmentWeatherListBinding
import com.example.kotlin_gb.viewmodel.AppState
import com.example.kotlin_gb.viewmodel.WeatherListViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment() {

    private lateinit var binding: FragmentWeatherListBinding
    lateinit var viewModel: WeatherListViewModel

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWeatherListBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Creating ViewModel
        viewModel = ViewModelProvider(this)[WeatherListViewModel::class.java]
        // Using ViewModel
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }
        })
        viewModel.sendRequest()
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.flLoadingLayout.visibility = View.GONE
                val result = appState.error
                Snackbar.make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.sendRequest() }
                    .show()

            }
            AppState.Loading -> {
                binding.flLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                val result = appState.weatherData
                binding.flLoadingLayout.visibility = View.GONE
                binding.cityName.text = result.city.name
                binding.cityCoordinates.text = "${result.city.lat} ${result.city.lon}"
                binding.feelsLikeValue.text = result.feelsLike.toString()
                binding.temperatureValue.text = result.temperature.toString()
                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}