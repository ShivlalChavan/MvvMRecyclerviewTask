package com.example.paginationtask.data.source.remote

import com.e.vencenttask.data.model.SearchResults

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("get_manufacturer_raw_material_list")
    suspend fun discover(): Response<SearchResults>
}