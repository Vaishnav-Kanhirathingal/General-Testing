package com.example.work_manager

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val car: Car
) : ViewModel() {
    private val TAG = this::class.simpleName

    init {
        Log.d(TAG, "instance created")
    }

    fun check() {
        Log.d(
            TAG, "sound = ${car.sound} | " +
                    "price = ${car.price} | " +
                    "taxPercentage = ${car.taxPercentage}"
        )
    }
}