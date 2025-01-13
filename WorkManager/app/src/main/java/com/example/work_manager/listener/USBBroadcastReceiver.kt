package com.example.work_manager.listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class USBBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val CUSTOM_CALL = "com.example.work_manager.CUSTOM_CALL"
    }

    private val TAG = this::class.simpleName

    init {
        Log.d(TAG, "listener started")
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "action = ${intent?.action}")
    }
}