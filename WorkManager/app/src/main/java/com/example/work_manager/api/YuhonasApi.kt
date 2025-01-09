package com.example.work_manager.api

import com.google.gson.JsonArray
import retrofit2.Response
import retrofit2.http.GET

// https://raw.githubusercontent.com/yuhonas/free-exercise-db/main/dist/exercises.json

interface YuhonasApi {
    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/yuhonas/"
    }

    @GET("free-exercise-db/main/dist/exercises.json")
    suspend fun getExerciseArray(): Response<JsonArray>
}