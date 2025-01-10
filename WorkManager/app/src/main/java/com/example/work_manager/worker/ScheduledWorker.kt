package com.example.work_manager.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.work_manager.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class ScheduledWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(
    appContext = appContext,
    params = params
) {

    companion object {
        private val TAG = this::class.simpleName
        const val START_TIME_KEY = "START_TIME_KEY"

        private const val SCHEDULED_WORK_CHANNEL_ID = "SCHEDULED_WORK_CHANNEL_ID"

        private fun showNotification(
            context: Context,
            startTime: Long?
        ) {
            //--------------------------------------------------------------------------create-a-channel
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(
                    NotificationChannel(
                        SCHEDULED_WORK_CHANNEL_ID,
                        "Scheduled work",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                        .apply { description = "This is an example notification channel" }
                )
            val text = startTime?.let {
                "START TIME = ${TimeStamps.toTimeStamp(time = it)}\n" +
                        "CURRENT = ${TimeStamps.toTimeStamp(time = System.currentTimeMillis())}"
            } ?: "START TIME IS NULL"
            //-------------------------------------------------------------------building-a-notification
            val builder = NotificationCompat
                .Builder(context, SCHEDULED_WORK_CHANNEL_ID) // setting the channel
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Scheduled Work")
                .setContentText(text) // setting the one line content text
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(text)
                ) // setting the multi line text
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false)
            //-----------------------------------------------------------------check-permission-and-show
            if (
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat
                    .from(context)
                    .notify(
                        Random.nextInt(), // random id assigned. notifications with same id and channel get overridden
                        builder.build()
                    )
            } else {
                Log.e(TAG, "notification permission denied")
            }
        }
    }

    override suspend fun doWork(): Result {
        try {
            val startTime = inputData
                .getLong(
                    key = START_TIME_KEY,
                    defaultValue = -1L
                )
                .takeUnless { it == -1L }
            showNotification(
                context = this.applicationContext,
                startTime = startTime
            )
            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }
}

object TimeStamps {
    private val formatter
        get() = SimpleDateFormat(
            "HH:mm:ss.SSS",
            Locale.getDefault()
        )

    fun fromTimeStamp(string: String): Long {
        val t = string.split('+').first()
        return formatter
            .parse(t)!!.time
    }

    fun toTimeStamp(time: Long): String {
        val t = Date(time)
        val s = formatter.format(t)
        return s
    }
}