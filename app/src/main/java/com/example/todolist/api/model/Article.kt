package com.example.todolist.api.model

data class Article(
    val articles: List<ArticleX>,
    val status: String,
    val totalResults: Int
)