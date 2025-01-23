package com.example.work_manager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

@HiltAndroidApp
class TestApplication : Application(){
    companion object{
        val client = HttpClient(
            engineFactory = Android,
            block = {
                this.engine {
                    val t = 10_0000
                    this.connectTimeout = t
                    this.socketTimeout = t
                }
            }
        )
    }
}