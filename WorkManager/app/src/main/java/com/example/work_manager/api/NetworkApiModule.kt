package com.example.work_manager.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkApiModule {
    const val TEST_CLASS_ONE_NAME = "TEST_CLASS_ONE_NAME"
    const val TEST_CLASS_TWO_NAME = "TEST_CLASS_TWO_NAME"

    @Provides
    @Singleton
    fun provideMinifiedApi(): MinifiedApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MinifiedApi.BASE_URL)
            .build()
            .create(MinifiedApi::class.java)

    @Provides
    @Singleton
    fun provideYuhonasApi(): YuhonasApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(YuhonasApi.BASE_URL)
            .build()
            .create(YuhonasApi::class.java)

    @Provides
    @Singleton
    @Named(TEST_CLASS_ONE_NAME)
    fun providesTestClassForDI1(): TestClassForDI = TestClassForDI()

    @Provides
    @Singleton
    @Named(TEST_CLASS_TWO_NAME)
    fun providesTestClassForDI2(): TestClassForDI = TestClassForDI().apply { this.counter = 10 }
}

class TestClassForDI {
    var counter = 0
}


