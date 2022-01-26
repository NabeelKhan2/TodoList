package com.example.todolist.data.sharedviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.db.model.Todo
import com.example.todolist.data.repository.TodoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepo) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> get() = _state

    private val _share = MutableSharedFlow<String>()
    val share: SharedFlow<String> get() = _share


    init {
        readAllData()
    }

    private fun readAllData() {
        viewModelScope.launch {
            repository.readAllData().collect {
                _state.value = State.Success(it)
                _share.emit("Successful")
            }
        }
    }

    fun addUser(user: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }


    sealed class State {
        data class Success(val userDetails: List<Todo>) : State()
        object Idle : State()
    }

}