package com.example.todolist.ui.updatefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.databinding.FragmentUpdateBinding
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist.db.model.Todo
import com.example.todolist.data.sharedviewmodel.TodoViewModel
import com.example.todolist.utils.extensions.alert
import com.example.todolist.utils.extensions.negativeButton
import com.example.todolist.utils.extensions.positiveButton
import com.example.todolist.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update) {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private val viewModel by viewModels<TodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentUpdateBinding.bind(view)

        binding.apply {
            updateFirstNameEt.setText(args.currentUser.FirstName)
            updateLastNameEt.setText(args.currentUser.lastName)
            updateAgeEt.setText(args.currentUser.age.toString())
            updateBtn.setOnClickListener {
                updateItem()
            }
        }

        // Add menu
        setHasOptionsMenu(true)

    }

    private fun updateItem() {

        val firstName = binding.updateFirstNameEt.text.toString()
        val lastName = binding.updateLastNameEt.text.toString()
        val age = Integer.parseInt(binding.updateAgeEt.text.toString())

        if (inputCheck(firstName, lastName, binding.updateAgeEt.text)) {
            // Create User Object
            val updatedUser = Todo(args.currentUser.id, firstName, lastName, age)
            // Update Current User
            viewModel.updateUser(updatedUser)
            toast("updated successfully!")
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            toast("please fill out all fields.")
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {

        alert {
            setTitle("Delete ${args.currentUser.FirstName}?")
            setMessage("Are you sure you want to delete ${args.currentUser.FirstName}?")
            positiveButton {
                viewModel.deleteUser(args.currentUser)
                toast("Successfully removed: ${args.currentUser.FirstName}")
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            negativeButton {}
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}