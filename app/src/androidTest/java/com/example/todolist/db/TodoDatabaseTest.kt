package com.example.todolist.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todolist.db.model.Todo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TodoDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TodoDatabase
    private lateinit var dao: TodoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TodoDatabase::class.java
        ).build()
        dao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addUser() = runBlockingTest {
        val user = Todo(1, "nabeel", "khan", 20)
        dao.addUser(user)

        val allUser = dao.readAllData().take(1).toList()

        assertThat(allUser[0]).contains(user)
    }

    @Test
    fun updateUser() = runBlockingTest {
        var user = Todo(1, "nabeel", "khan", 20)
        dao.addUser(user)
        user = Todo(1, "Uzair", "Khan", 24)
        dao.updateUser(user)
        val allUser = dao.readAllData().take(1).toList()
        assertThat(allUser[0]).contains(user)
    }

    @Test
    fun deleteUser() = runBlockingTest {
        val user = Todo(1, "nabeel", "khan", 20)
        dao.addUser(user)
        dao.deleteUser(user)
        val allUser = dao.readAllData().take(1).toList()
        assertThat(allUser[0]).doesNotContain(user)
    }

    @Test
    fun deleteAllUser() = runBlockingTest {
        val user1 = Todo(1, "nabeel", "khan", 20)
        dao.addUser(user1)
        val user2 = Todo(2, "nabeel", "khan", 202)
        dao.addUser(user2)
        dao.deleteAllUsers()

        val allUser = dao.readAllData().take(1).toList()[0]
        assertThat(allUser).doesNotContain(user1)

        val allUsers = dao.readAllData().take(1).toList()[0]
        assertThat(allUsers).doesNotContain(user2)

    }

}