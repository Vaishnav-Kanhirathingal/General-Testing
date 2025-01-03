package com.example.work_manager

import android.util.Log
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
        }
    }
}