package com.example.kotlin_gb.view.weatherlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentWeatherListBinding
import com.example.kotlin_gb.utils.Const
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
    private var isRussian = true

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
        // Creating ViewModel
        viewModel = ViewModelProvider(this)[WeatherListViewModel::class.java]

        viewModel.getLiveData().observe(viewLifecycleOwner) { it -> renderData(it) }

        viewModel.getRussianList()

        binding.fab.setOnClickListener {
            isRussian = !isRussian
            showWeatherListAndIcon(isRussian)
        }
    }

    private fun showWeatherListAndIcon(isRussian: Boolean) {
        if (isRussian) {
            viewModel.getRussianList()
            binding.fab.apply {
                setImageResource(R.drawable.flag_russia)
            }
        } else {
            viewModel.getWorldList()
            binding.fab.setImageResource(R.drawable.flag_world)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.showError()
                binding.root.showSnackErrorWithAction(
                    R.string.snack_bar_error_title.toString(),
                    Snackbar.LENGTH_INDEFINITE,
                    R.string.snack_bar_reload_title.toString(),
                ) { showWeatherListAndIcon(isRussian) }
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
                rv.adapter = WeatherListAdapter(
                    result
                ) { weather ->
                    requireActivity().supportFragmentManager.beginTransaction()
                        .hide(this@WeatherListFragment)
                        .add(R.id.container, WeatherDetailsFragment.newInstance(weather))
                        .addToBackStack("")
                        .commit()
                }
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

    // Функция-расширение
    private fun View.showSnackErrorWithAction(
        errorTitle: String = R.string.snack_bar_error_title.toString(),
        snackDuration: Int,
        setActionName: String,
        block: (v: View) -> Unit
    ) {
        Snackbar.make(this, errorTitle, snackDuration).setAction(setActionName, block).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}