package com.example.kotlin_gb.view.weatherlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentWeatherListBinding
import com.example.kotlin_gb.model.Weather
import com.example.kotlin_gb.view.details.OnCityClickable
import com.example.kotlin_gb.view.details.WeatherDetailsFragment
import com.example.kotlin_gb.viewmodel.AppState
import com.example.kotlin_gb.viewmodel.WeatherListViewModel

class WeatherListFragment : Fragment(), OnCityClickable {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    private lateinit var viewModel: WeatherListViewModel
    private var isRussian = true

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherListBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Creating ViewModel
        viewModel = ViewModelProvider(this)[WeatherListViewModel::class.java]
        // Using ViewModel
        /*viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }
        })*/
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        viewModel.getRussianList()

        binding.fab.setOnClickListener {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getRussianList()
                binding.fab.setImageResource(R.drawable.flag_russia)
            } else {
                viewModel.getWorldList()
                binding.fab.setImageResource(R.drawable.flag_world)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.showResult()
            }
            is AppState.Loading -> {
                binding.loading()
            }
            is AppState.SuccessSingleWeather -> {
                //val result = appState.weatherData
                binding.showResult()
            }
            is AppState.SuccessMultiWeather -> {
                binding.showResult()
                val result = appState.weatherListData
                val rv = binding.rvWeatherList
                rv.layoutManager = LinearLayoutManager(requireActivity())
                rv.adapter = WeatherListAdapter(result, this)
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

    override fun onCityClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this)
            .add(R.id.container, WeatherDetailsFragment.newInstance(weather)).addToBackStack("")
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}