package com.example.todolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.db.model.Todo

@Database(
    entities = [Todo::class],
    version = 2,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun userDao(): TodoDao

}