package com.maxi.newscleanarchitecture.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maxi.newscleanarchitecture.databinding.ItemNewsBinding
import com.maxi.newscleanarchitecture.domain.model.Article

class NewsAdapter(
    private val articles: ArrayList<Article>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(article: Article) {
            binding.apply {
                article.apply {
                    tvTitle.text = title
                    tvDescp.text = description
                    tvUrl.text = url
                    Glide.with(binding.root.context)
                        .load(imageUrl)
                        .into(ivNews)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int =
        articles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(articles[position])
    }

    fun setData(articles: List<Article>) {
        this.articles.apply {
            clear()
            addAll(articles)
        }
        notifyDataSetChanged()
    }
}