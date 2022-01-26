package com.example.todolist.data.repository
import com.example.todolist.api.NewsService
import com.example.todolist.db.TodoDao
import com.example.todolist.api.model.Article
import com.example.todolist.db.model.Todo
import com.example.todolist.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val userDao: TodoDao,
    private val api : NewsService
    ) : TodoRepo {

    override fun readAllData() : Flow<List<Todo>> = userDao.readAllData()

    override suspend fun addUser(user: Todo) {
        userDao.addUser(user)
    }

    override suspend fun updateUser(user: Todo) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: Todo) {
        userDao.deleteUser(user)
    }

    override suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

    override fun getNews(country: String): Flow<Resource<Article>> = flow {
            try {
                val response = api.getHeadlines(country)
                val result = response.body()
                if (response.isSuccessful && result != null) {
                  emit(Resource.Success(result))
                } else {
                    emit(Resource.Error<Article>(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error<Article>(e.message ?: "An error occurred"))
            }
        }


}