package com.example.todolist.data.repository

import com.example.todolist.api.model.Article
import com.example.todolist.db.model.Todo
import com.example.todolist.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TodoRepo {

    suspend fun addUser(user: Todo)
    suspend fun updateUser(user: Todo)
    suspend fun deleteUser(user: Todo)
    suspend fun deleteAllUsers()

    fun readAllData() : Flow<List<Todo>>

     fun getNews(country: String): Flow<Resource<Article>>

}