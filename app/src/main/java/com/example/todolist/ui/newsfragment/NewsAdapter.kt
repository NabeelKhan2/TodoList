package com.example.todolist.ui.newsfragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.todolist.databinding.NewsItemBinding
import com.example.todolist.api.model.ArticleX

class NewsAdapter(private val articleList: List<ArticleX>?) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var selectedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList?.size ?: 0
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.bind(articleList?.get(position))
    }


    inner class MyViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: ArticleX?) {
            binding.apply {
                if (item != null) {
                    tvTitle.text = item.title
                }
                if (item != null) {
                    tvDescription.text = item.description as CharSequence?
                }
                if (item != null) {
                    img.load(item.urlToImage as String)
                }

                itemView.setOnClickListener {
                    selectedItemPosition = adapterPosition
                    notifyDataSetChanged()
                }
                if (selectedItemPosition == adapterPosition)
                    clNews.setBackgroundColor(Color.parseColor("#DC746C"))
                else
                    clNews.setBackgroundColor(Color.parseColor("#FFFFFF"))

            }
        }
    }


}