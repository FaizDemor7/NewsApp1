package com.example.newsapp

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdaptor (private val listener:OnNewsClick): RecyclerView.Adapter<NewsAdaptor.NewsViewHolder>() {
    private val items= ArrayList<News>()

    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val titleView: TextView= itemView.findViewById(R.id.title)
        val author: TextView = itemView.findViewById(R.id.author)
        val image : ImageView= itemView.findViewById(R.id.image)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = items[position]
        holder.titleView.text=currentNews.title
        holder.author.text=currentNews.author

        // we need to access the image from imageUrl so some changes we use glide

        Glide.with(holder.itemView.context).load(currentNews.imageUrl).into(holder.image)


    }
    override fun getItemCount(): Int {
        return items.size
    }
    fun updateData(newData:ArrayList<News>){
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()

    }
}

// now we need to perform click event with chrome to get full details of news
interface OnNewsClick{
    fun onClicked(news:News)
}
