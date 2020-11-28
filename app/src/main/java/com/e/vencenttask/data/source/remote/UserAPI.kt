package com.example.paginationtask.data.source.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserAPI {

    private const val baseUrl = "https://tirumalaindustries.in/api/web/"

    val movieService: UserService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        return@lazy retrofit.create(UserService::class.java)
    }
}