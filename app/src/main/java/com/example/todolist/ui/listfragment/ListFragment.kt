package com.example.todolist.ui.listfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.todolist.R
import com.example.todolist.databinding.FragmentListBinding
import com.example.todolist.data.sharedviewmodel.TodoViewModel
import android.view.*
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.ui.adapter.TodoAdapter
import com.example.todolist.utils.extensions.alert
import com.example.todolist.utils.extensions.negativeButton
import com.example.todolist.utils.extensions.positiveButton
import com.example.todolist.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        binding.apply {
            floatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
            btnNews.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_newsFragment)
            }
        }

        // Add menu
        setHasOptionsMenu(true)
        initObserver()
    }

    private fun initObserver() {
        // UserViewModel
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is TodoViewModel.State.Success -> {
                        val adapter = TodoAdapter(it.userDetails)
                        val recyclerView = binding.recyclerview
                        recyclerView.apply {
                            this.adapter = adapter
                            layoutManager = LinearLayoutManager(requireContext())
                        }

                    }
                    else -> {
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.share.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {

        alert {
            setTitle("Delete everything?")
            setMessage("Are you sure you want to delete everything?")
            positiveButton {
                viewModel.deleteAllUsers()
                toast("Successfully removed everything")
            }
            negativeButton { }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


