package com.example.todolist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.CustomRowBinding
import com.example.todolist.db.model.Todo
import com.example.todolist.ui.listfragment.ListFragmentDirections


class TodoAdapter(private val user: List<Todo>) : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.user.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(this.user[position])
    }

    inner class MyViewHolder(private val binding: CustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Todo) {
            binding.apply {
                idTxt.text = item.id.toString()
                firstNameTxt.text = item.FirstName
                lastNameTxt.text = item.lastName
                ageTxt.text = item.age.toString()

                rowLayout.setOnClickListener {
                    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(item)
                    binding.root.findNavController().navigate(action)

                }
            }
        }
    }
}
