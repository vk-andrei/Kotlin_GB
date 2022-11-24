package com.example.kotlin_gb.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.FragmentHistoryBinding
import com.example.kotlin_gb.utils.Utils.hide
import com.example.kotlin_gb.utils.Utils.show
import com.example.kotlin_gb.utils.Utils.showSnackErrorWithAction
import com.example.kotlin_gb.viewmodel.AppState
import com.example.kotlin_gb.viewmodel.HistoryViewModel
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this)[HistoryViewModel::class.java]
    }
    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        rvHistoryFragment.adapter = historyAdapter
        rvHistoryFragment.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.historyLiveData.observe(viewLifecycleOwner, Observer { renderDate(it) })
        viewModel.getAllHistory()


    }

    private fun renderDate(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.rvHistoryFragment.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                binding.rvHistoryFragment.showSnackErrorWithAction(
                    getString(R.string.snack_bar_error_title), Snackbar.LENGTH_INDEFINITE,
                    getString(R.string.snack_bar_reload_title),
                    { viewModel.getAllHistory() }
                )
            }
            is AppState.Loading -> {
                binding.rvHistoryFragment.hide()
                binding.includedLoadingLayout.loadingLayout.show()
            }
            is AppState.SuccessMultiWeather -> {
                binding.rvHistoryFragment.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                historyAdapter.setData(appState.weatherListData)
            }
            is AppState.SuccessSingleWeather -> TODO()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}