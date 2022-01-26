package com.example.todolist.ui.newsfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ArticleViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)

        initObserver()

    }

    private fun initObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is ArticleViewModel.State.Success -> {
                        val adapter = NewsAdapter(it.article?.articles)
                        val recyclerView = binding.rvNews
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    }
                    is ArticleViewModel.State.Error -> {
                        it.msg?.let { it1 -> Log.d("TAG", it1) }
                    }
                    else -> {
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}