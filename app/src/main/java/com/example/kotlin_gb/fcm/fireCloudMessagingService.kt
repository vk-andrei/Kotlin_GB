package com.example.kotlin_gb.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.kotlin_gb.R
import com.example.kotlin_gb.utils.Const
import com.example.kotlin_gb.utils.Const.Companion.NOTIFICATION_ID_FIRE_CLOUD
import com.example.kotlin_gb.utils.Const.Companion.NOTIFICATION_KEY_MESSAGE
import com.example.kotlin_gb.utils.Const.Companion.NOTIFICATION_KEY_TITLE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {

        // ВЫЗЫВАЕТСЯ ЕДИНЫЖДЫ!!!
        Log.d("SERVICE", "token = $token")
        // ckUiXYM1TvGREDh9TegChr:APA91bH_ko_5pWZXkBznIEdDVx80V_kNk9LAPGL8b3q8Lir9635SicwGcyHiG9qswXLIpj7_ZTtzlCpRCVdQwZFqLLETgGaGUfXUELPle5_hOcGrNB8YKGNnrCei_CxxAFARaYTZv4NQ
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("SERVICE", "message = $message")

        val data = message.data
        val title = data[NOTIFICATION_KEY_TITLE]
        val body = data[NOTIFICATION_KEY_MESSAGE]
        if (!title.isNullOrEmpty() && !body.isNullOrEmpty()) {
            pushNotificationFromFCM(title, body)
        }
        super.onMessageReceived(message)
    }

    private fun pushNotificationFromFCM(title: String, body: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationHigh =
            NotificationCompat.Builder(this, Const.CHANNEL_HIGH_PRIORITY_ID).apply {
                setContentTitle(title)
                setContentText(body)
                setSmallIcon(R.drawable.ic_marker_google_map)
                priority = NotificationCompat.PRIORITY_MAX
            }.build()
        // После 26 версии доступно НЕСКОЛЬКО каналов для ПУШЕЙ (пользователь может отключать или все или некоторые)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelHigh = NotificationChannel(
                Const.CHANNEL_HIGH_PRIORITY_ID,
                "CHANNEL HIGH",
                NotificationManager.IMPORTANCE_HIGH
            )
            channelHigh.description = "Channel for importance notifications"
            notificationManager.createNotificationChannel(channelHigh)
        }
        // до 26 версии ТОЛЬКО один канал для пушей: (Эта строчка нужна и для версии выше 26!)
        notificationManager.notify(NOTIFICATION_ID_FIRE_CLOUD, notificationHigh)
    }
}