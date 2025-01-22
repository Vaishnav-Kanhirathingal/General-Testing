package com.example.work_manager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class TestService : Service() {
    private val TAG = this::class.simpleName

    companion object {
        private const val CHANNEL_ID = "DownloadChannel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Downloading File")
            .setContentText("Download in progress...")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .build()
        startForeground(1, notification)
        Thread {
            downloadFile()
            Log.d(TAG, "download finished")
            stopSelf()
            Log.d(TAG, "command completed")
        }.start()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // Not binding the service
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Download Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
    }

    private fun downloadFile() {
        Thread.sleep(5000) // Simulates a 5-second download
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "service destroyed")
    }
}

