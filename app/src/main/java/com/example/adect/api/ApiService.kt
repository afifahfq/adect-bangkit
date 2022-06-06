package com.example.adect.api

import com.example.adect.models.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    fun getArticles(
        @Query("q") q: String? = "anemia",
        @Query("sortBy") sortBy: String? = "popularity",
        @Query("language") language: String? = "en",
        @Query("apiKey") apiKey: String? = "74b833108c194e9bb3edbb36b22bd8b5"
    ): Call<ArticlesResponse>
}