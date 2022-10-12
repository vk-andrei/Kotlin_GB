package com.example.kotlin_gb.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_gb.databinding.FragmentWeatherListBinding

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
        viewModel = ViewModelProvider(this)[WeatherListViewModel::class.java]
        viewModel.liveData.observe(viewLifecycleOwner, object : Observer<Any> {
            override fun onChanged(t: Any?) {
                Toast.makeText(requireContext(), "IS WORKING!! $t", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.sendRequest()
    }
}