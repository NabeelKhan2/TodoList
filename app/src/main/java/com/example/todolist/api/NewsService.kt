package com.example.todolist.api

import com.example.todolist.api.model.Article
import com.example.todolist.utils.extensions.Constants.apiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface NewsService {
    @GET("v2/top-headlines?apiKey=$apiKey")
    suspend fun getHeadlines(@Query("country") country: String) : Response<Article>
}