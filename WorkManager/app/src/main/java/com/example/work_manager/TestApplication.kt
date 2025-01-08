package com.example.work_manager

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class TestApplication : Application()

@Module
@InstallIn(SingletonComponent::class)
object DependencyCollection {
    @Provides
    fun getCarObject(): Car = Car(
        sound = "sample",
        price = 1000,
        taxPercentage = 200
    )
}
