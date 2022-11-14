package com.example.kotlin_gb.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.lang.StringBuilder

// (FOR TEST) -> add in manifest - CHANGING AIRPLANE MODE
class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        StringBuilder().apply {
            append("MSG from system:\n")
            append("action: ${intent.action}")
            toString()
        }.also {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
}
