package com.example.work_manager.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkApiModule {

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
    fun providesTestClassForDI(): TestClassForDI = TestClassForDI()
}

class TestClassForDI {
    var counter = 0
}