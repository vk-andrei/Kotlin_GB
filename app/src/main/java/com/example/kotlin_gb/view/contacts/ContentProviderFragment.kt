package com.example.kotlin_gb.view.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.kotlin_gb.databinding.FragmentContentProviderBinding

class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
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
        Toast.makeText(context, "Getting contacts....", Toast.LENGTH_LONG).show()
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                //TODO Permission is GRANTED
            } else {
                //TODO Permission is DENIED
            }
        }


    companion object {
        fun newInstance() = ContentProviderFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}