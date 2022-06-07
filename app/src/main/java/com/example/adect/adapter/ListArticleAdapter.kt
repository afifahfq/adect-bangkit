package com.example.adect.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adect.R
import com.example.adect.models.Article

class ListArticleAdapter(private val listArticles: ArrayList<Article>) : RecyclerView.Adapter<ListArticleAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListArticleAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_article, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListArticleAdapter.ListViewHolder, position: Int) {
        val (author, title, description, url, urlToImage, publishedAt, content) = listArticles[position]
        Glide.with(holder.itemView.context)
            .load(urlToImage)
            .into(holder.imgPhoto)

        holder.tvTitle.text = title
        holder.tvAuthor.text = "by " + author
        holder.tvDescription.text = description
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listArticles[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listArticles.size
    }

    class ListViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        var tvAuthor: TextView = itemView.findViewById(R.id.tv_item_author)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Article)
    }
}