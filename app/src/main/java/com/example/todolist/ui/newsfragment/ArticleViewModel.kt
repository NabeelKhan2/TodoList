package com.example.todolist.ui.newsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.api.model.Article
import com.example.todolist.data.repository.TodoRepo
import com.example.todolist.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repo: TodoRepo) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> get() = _state

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            repo.getNews("in").collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = State.Success(it.data)
                    }
                    is Resource.Error -> {
                        _state.value = State.Error(it.message)
                    }
                }

            }
        }
    }


    sealed class State {
        data class Success(val article: Article?) : State()
        data class Error(val msg: String?) : State()
        object Idle : State()
    }

}