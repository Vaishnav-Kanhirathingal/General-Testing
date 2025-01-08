package com.example.work_manager.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SimpleWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(
    appContext = appContext,
    params = params
) {
    private val TAG = this::class.simpleName

    override suspend fun doWork(): Result {
        try {
            var str = ""
            repeat(
                times = 5,
                action = { str += "Request = $it | " }
            )
            Log.d(TAG, str)
            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }
}