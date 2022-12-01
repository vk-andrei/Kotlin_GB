package com.example.kotlin_gb.view.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_gb.databinding.FragmentContentProviderContactsBinding

class ContentProviderForContactsFragment : Fragment() {

    private var _binding: FragmentContentProviderContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        // TODO
    }


    companion object {
        fun newInstance() = ContentProviderForContactsFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}