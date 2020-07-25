package com.example.dynamictabview.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamictabview.Model.Model
import com.example.dynamictabview.R
import com.example.dynamictabview.activity.BriefNewsActivity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.regex.Pattern

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList : ArrayList<Model>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_world_news,parent,false)
        val newsViewHolder = NewsViewHolder(view)

        newsViewHolder.selectedCardNews.setOnClickListener {

            val intent = Intent(parent.context,BriefNewsActivity :: class.java)
            intent.apply {
                putExtra("newsOfJournal",newsList?.get(newsViewHolder.adapterPosition)?.nameOfJournal)
                putExtra("worldNewsImage",newsList?.get(newsViewHolder.adapterPosition)?.worldImageView)
                putExtra("worldnewsTitle",newsList?.get(newsViewHolder.adapterPosition)?.worldheadLine)
                putExtra("authorname",newsList?.get(newsViewHolder.adapterPosition)?.authorName)
                putExtra("postedDate",newsList?.get(newsViewHolder.adapterPosition)?.worldNewsPostDate)
                putExtra("content",newsList?.get(newsViewHolder.adapterPosition)?.newsContent)
                putExtra("fullnewsurl",newsList?.get(newsViewHolder.adapterPosition)?.fullContentRead)
            }
            parent.context.startActivity(intent)
        }
        return newsViewHolder
    }

    override fun getItemCount(): Int {
        return newsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        newsList?.get(position)?.let { holder.bindViewHolder(it) }
    }
    fun setWorldNews(notes: ArrayList<Model>?){
        this.newsList = notes
        notifyDataSetChanged()
    }
    class NewsViewHolder(itview: View) : RecyclerView.ViewHolder(itview) {

        var newImageView  = itview.findViewById(R.id.imageViewinnewsfeed) as ImageView
        var textViewHeadlineNews = itview.findViewById(R.id.textViewHeadline) as TextView
        var newsInBrief = itview.findViewById(R.id.newsInBrief) as TextView
        var postedTime = itview.findViewById(R.id.postdate) as TextView

        var selectedCardNews =  itview.findViewById(R.id.worldnewscardview) as CardView
        private val REMOVE_TAGS = Pattern.compile("<.+?>")

        fun bindViewHolder(worldNewsFeedModel : Model){

            Picasso.get().load(worldNewsFeedModel.worldImageView).into(newImageView)

            val removeHtmlTag = removeTags(worldNewsFeedModel.worldNewsBody)

            textViewHeadlineNews.text = worldNewsFeedModel?.worldheadLine
            newsInBrief.setText(removeHtmlTag)

            val postedTimeChangeFormat = worldNewsFeedModel?.worldNewsPostDate

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val outputFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
            val date = inputFormat.parse(postedTimeChangeFormat)
            val formattedDate = outputFormat.format(date)

            postedTime.text = "Date : "+formattedDate
        }
        fun removeTags(removeHtmlTagInNews : String?): String? {
            if (removeHtmlTagInNews == null || removeHtmlTagInNews.length === 0) {
                return removeHtmlTagInNews
            }
            val m = REMOVE_TAGS.matcher(removeHtmlTagInNews)
            return m.replaceAll("")
        }
    }
}