package com.example.kotlin_gb.view.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_gb.databinding.FragmentContactsBinding
import com.example.kotlin_gb.utils.Utils.hide
import com.example.kotlin_gb.utils.Utils.show
import com.example.kotlin_gb.viewmodel.AppStateContacts
import com.example.kotlin_gb.viewmodel.ContactsViewModel

class ContactsFragment : Fragment() {

    private val contactsViewModel: ContactsViewModel by lazy {
        ViewModelProvider(this)[ContactsViewModel::class.java]
    }

    private val contactsAdapter: ContactsAdapter by lazy {
        ContactsAdapter()
    }

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(layoutInflater, container, false)
        binding.rvContactsList.adapter = contactsAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        contactsViewModel.contacts.observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    private fun renderData(appStateContacts: AppStateContacts) {
        when (appStateContacts) {
            is AppStateContacts.Loading -> {
                binding.rvContactsList.hide()
                binding.includedLoadingLayout.root.show()
            }
            is AppStateContacts.Success -> {
                binding.rvContactsList.show()
                binding.includedLoadingLayout.root.hide()
                contactsAdapter.contactsList = appStateContacts.contacts
            }
        }
    }

    // Проверяем, разрешено ли чтение контактов
    private fun checkPermission() {
        context?.let {
            when {
                //Проверяем случай, если разрешение на доступ к контактам уже дано. Если это так, то
                //вызываем Content Provider для получения контактов. Как мы уже писали выше, потребуется
                //каждый раз проверять разрешение, потому что в любой момент пользователь может его
                //отменить.
                ContextCompat.checkSelfPermission(
                    it, Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //Доступ к контактам на телефоне есть
                    getContacts()
                }
                //Опционально: если нужно пояснение перед запросом разрешений
                //Отображаем пояснение перед запросом разрешения. Вызываем этот метод, если считаем
                //важным пояснить пользователю приложения, зачем нам требуется доступ к данным на
                //смартфоне. Обычно в роли пояснения выступает диалоговое окно, всплывающее перед тем,
                //как запрашивать доступ.
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) ->
                    AlertDialog.Builder(it)
                        .setTitle("Access to Contacts")
                        .setMessage("Explanation")
                        .setPositiveButton("Grant Access") { _, _ ->
                            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                        }
                        .setNegativeButton("Deny Access") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                //Если разрешения нет и объяснение отображать не надо, запрашиваем разрешение.
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }
            }
        }
    }

    private fun getContacts() {
        Toast.makeText(context, "Getting contacts....", Toast.LENGTH_SHORT).show()
        contactsViewModel.getContacts()
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                //Permission is GRANTED
                getContacts()
            } else {
                //Permission is DENIED
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("Access to Contacts")
                        .setMessage("Explanation")
                        .setNegativeButton("Deny Access") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
            }
        }

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}