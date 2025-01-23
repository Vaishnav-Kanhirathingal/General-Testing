package com.example.work_manager

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.work_manager.databinding.ActivityMainBinding
import com.example.work_manager.listener.USBBroadcastReceiver
import com.example.work_manager.worker.ScheduledWorker
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val receiver = USBBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(
            receiver,
            IntentFilter(USBBroadcastReceiver.CUSTOM_CALL),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Context.RECEIVER_EXPORTED else 0
        )


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
        binding.test1Button.setOnClickListener { lifecycleScope.launch { viewModel.checkEverything() } }
        binding.test2Button.setOnClickListener { lifecycleScope.launch { viewModel.checkSingleton() } }
        binding.test3Button.setOnClickListener {
            val intent = Intent(USBBroadcastReceiver.CUSTOM_CALL)
            Log.d(TAG, "b3 pressed, action = ${intent.action}")
            this.sendBroadcast(intent)
        }
        val serviceIntent = Intent(this, TestService::class.java)

        binding.test4Button.setOnClickListener { this.startService(serviceIntent) }
        binding.test5Button.setOnClickListener { this.stopService(serviceIntent) }
        binding.test6Button.setOnClickListener {
            lifecycleScope.launch {
                try {
                    TestApplication.client.get(
                        urlString = "https://raw.githubusercontent.com/exercemus/exercises/refs/heads/main/exercises.json"
                    ).let { res: HttpResponse ->
                        Log.d(TAG, "res.bodyAsText = ${res.bodyAsText()}")
                        res.body<JsonObject>(typeInfo = TypeInfo(JsonObject::class)).let { json ->
                            Log.d(
                                TAG, "json = " +
                                        GsonBuilder().setPrettyPrinting().create().toJson(json)
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}