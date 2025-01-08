package com.example.work_manager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.work_manager.databinding.ActivityMainBinding
import com.example.work_manager.worker.ScheduledWorker
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private val viewModel: MainViewModel by viewModels()
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
//                    .Builder(workerClass = SimpleWorker::class)
                    .Builder(workerClass = ScheduledWorker::class)
                    .setInputData(
                        inputData = workDataOf(
                            ScheduledWorker.START_TIME_KEY to System.currentTimeMillis()
                        )
                    )
                    .setConstraints(
                        constraints = Constraints.Builder()
                            .setRequiresCharging(requiresCharging = true)
                            .setRequiredNetworkType(networkType = NetworkType.UNMETERED)
                            .build()
                    )
                    .build()
            )
        }
        binding.periodicTestButton.setOnClickListener {
            WorkManager.getInstance(context = this).enqueue(
                request = PeriodicWorkRequest
                    .Builder(
                        workerClass = ScheduledWorker::class,
                        repeatInterval = 15,
                        repeatIntervalTimeUnit = TimeUnit.MINUTES
                    )
                    .setInputData(
                        inputData = workDataOf(
                            ScheduledWorker.START_TIME_KEY to System.currentTimeMillis()
                        )
                    )
                    .build()
            )
            Log.d(TAG, "work scheduled")
        }
        binding.testButton.setOnClickListener {
            viewModel.check()
        }
    }
}

class Car(
    val sound: String,
    val price: Int,
    val taxPercentage: Int,
)