package com.example.todolist.ui.addfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddBinding
import com.example.todolist.db.model.Todo
import com.example.todolist.data.sharedviewmodel.TodoViewModel
import android.text.Editable
import android.text.TextUtils
import androidx.navigation.fragment.findNavController
import com.example.todolist.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddBinding.bind(view)

        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }

    }

    private fun insertDataToDatabase() {

        val firstName = binding.addFirstNameEt.text.toString()
        val lastName = binding.addLastNameEt.text.toString()
        val age = binding.addAgeEt.text

        if (inputCheck(firstName, lastName, age)) {
            // Create User Object
            val todo = Todo(
                0,
                firstName,
                lastName,
                Integer.parseInt(age.toString())
            )
            // Add Data to Database
            viewModel.addUser(todo)
            toast("Successfully added")
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            toast("please fill out all fields.")
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || age.isEmpty())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

