package com.example.work_manager.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET

// https://raw.githubusercontent.com/exercemus/exercises/minified/minified-exercises.json

interface MinifiedApi {
    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/exercemus/"
    }

    @GET("exercises/minified/minified-exercises.json")
    suspend fun getExerciseDictionary(): Response<JsonObject>
}