package com.example.work_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomMessagingService : FirebaseMessagingService() {
    private val TAG = this::class.simpleName

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "new token = $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification.let {
            Log.d(
                TAG,
                if (it == null) "notification is null"
                else "notification = ${it.title} | ${it.body}"
            )
            if (it != null) {
                showNotification(
                    title = it.title,
                    body = it.body
                )
            }
        }
    }

    private fun showNotification(
        title: String?,
        body: String?
    ) {
        val channelId = "default_channel"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            "Default Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        // Intent to open the app when the notification is tapped
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        // Show the notification
        notificationManager.notify(0, notification)
    }
}