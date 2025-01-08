package com.example.work_manager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.work_manager.databinding.ActivityMainBinding
import com.example.work_manager.worker.SimpleWorker
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            Log.d(TAG, if (task.isSuccessful) "FCM Token: ${task.result}" else "task failed")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }
        applyBinding()
    }

    private fun applyBinding() {
        binding.oneTimeTestButton.setOnClickListener {
            WorkManager.getInstance(context = this).enqueue(
                request = OneTimeWorkRequest
                    .Builder(workerClass = SimpleWorker::class)
                    .build()
            )
        }
        binding.periodicTestButton.setOnClickListener {
            WorkManager.getInstance(context = this).enqueue(
                request = PeriodicWorkRequest.Builder(
                    workerClass = SimpleWorker::class,
                    repeatInterval = 15,
                    repeatIntervalTimeUnit = TimeUnit.MINUTES
                )
                    .build()
            )
            Log.d(TAG, "work scheduled")
        }
    }
}