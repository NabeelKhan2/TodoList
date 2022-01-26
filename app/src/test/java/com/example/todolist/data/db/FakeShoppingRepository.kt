package com.example.todolist.data.db

import com.example.todolist.api.model.Article
import com.example.todolist.data.repository.TodoRepo
import com.example.todolist.db.model.Todo
import com.example.todolist.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class FakeShoppingRepository : TodoRepo {

    private val todo = mutableListOf<Todo>()

    private val observableTodo = MutableStateFlow<List<Todo>>(todo)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshFLow() {
        observableTodo.value = todo
    }

    override suspend fun addUser(user: Todo) {
        todo.add(user)
        refreshFLow()
    }

    override suspend fun updateUser(user: Todo) {
        todo.add(user)
        refreshFLow()
    }

    override suspend fun deleteUser(user: Todo) {
        todo.remove(user)
        refreshFLow()
    }

    override suspend fun deleteAllUsers() {
        todo.clear()
    }

    override fun readAllData(): Flow<List<Todo>> {
        return observableTodo
    }

    override fun getNews(country: String): Flow<Resource<Article>> {
        return flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<Article>("Error"))
            } else {
                emit(Resource.Success<Article>(Article(listOf(), "", 0)))
            }
        }
    }


}