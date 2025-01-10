package com.example.work_manager

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.work_manager.api.MinifiedApi
import com.example.work_manager.api.NetworkApiModule
import com.example.work_manager.api.TestClassForDI
import com.example.work_manager.api.YuhonasApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    private val yuhonasApi: YuhonasApi,
    private val minifiedApi: MinifiedApi,
    @Named(NetworkApiModule.TEST_CLASS_ONE_NAME) private val testClassForDIOne: TestClassForDI,
    @Named(NetworkApiModule.TEST_CLASS_TWO_NAME) private val testClassForDITwo: TestClassForDI
) : ViewModel() {
    private val TAG = this::class.simpleName

    init {
        Log.d(TAG, "instance created")
    }

    suspend fun checkEverything(): Unit = withContext(Dispatchers.IO) {
        val callData1 = async { yuhonasApi.getExerciseArray() }
        val callData2 = async { minifiedApi.getExerciseDictionary() }
        callData1
            .await()
            .takeIf { it.isSuccessful }
            ?.let { Log.d(TAG, "callData1 = ${it.body().toString().take(n = 1000)}") }
            ?: Log.d(TAG, "cal1 1 unsuccessful")

        callData2
            .await()
            .takeIf { it.isSuccessful }
            ?.let { Log.d(TAG, "callData2 = ${it.body().toString().take(n = 1000)}") }
            ?: Log.d(TAG, "cal1 2 unsuccessful")
    }

    fun checkSingleton() {
        Log.d(
            TAG, "testClassForDIOne = ${testClassForDIOne.counter++} | " +
                    "testClassForDITwo = ${testClassForDITwo.counter++}"
        )
    }
}