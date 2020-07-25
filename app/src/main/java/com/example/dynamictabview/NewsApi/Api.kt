package com.example.dynamictabview.NewsApi

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("random/{id}")
     fun getData(@Path("id") id : Int) : Call<JsonObject>

    @GET("top-headlines")
    fun getAllTrendingNews(
        @Query("country") country : String,
        @Query("category") category : String,
        @Query("apiKey") apiKey : String
    ) : Call<JsonObject>

    @GET("top-headlines")
    fun getParticularNews(
        @Query("q") q : String,
        @Query("country") country : String,
        @Query("category") category : String,
        @Query("apiKey") apiKey : String
    ) : Call<JsonObject>
}