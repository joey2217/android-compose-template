package com.joey.template.data.remote

import com.joey.template.domain.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}