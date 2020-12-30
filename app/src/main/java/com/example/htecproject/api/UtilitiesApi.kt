package com.example.htecproject.api

import com.example.htecproject.model.Posts
import com.example.htecproject.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UtilitiesApi {
    @GET("posts")
    suspend fun getPosts() : List<Posts>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String) : User
}