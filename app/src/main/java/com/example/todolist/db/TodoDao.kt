package com.example.todolist.db

import androidx.room.*
import com.example.todolist.db.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(todo: Todo)

    @Update
    suspend fun updateUser(user: Todo)

    @Delete
    suspend fun deleteUser(user: Todo)

    @Query("DELETE FROM Todo")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM Todo ORDER BY id ASC")
    fun readAllData(): Flow<List<Todo>>

}