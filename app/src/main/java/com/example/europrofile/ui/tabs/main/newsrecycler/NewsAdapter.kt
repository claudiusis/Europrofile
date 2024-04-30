package com.example.europrofile.ui.tabs.main.newsrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.europrofile.R
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

class NewsAdapter(val list : MutableList<Image>, private val viewPager2: ViewPager2): RecyclerView.Adapter<NewsAdapter.NewsVH>() {

    inner class NewsVH(view: View) : RecyclerView.ViewHolder(view) {
        private val image: RoundedImageView = view.findViewById(R.id.imageView)
        fun onBind(item: Image) {
            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(R.color.blue)
                .error(R.color.nav_color)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_vh, parent, false)
        return NewsVH(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        holder.onBind(list[position])

        if (position == list.size - 2) {
            infinity(list)
        }
    }

    private fun infinity(list: MutableList<Image>) {
        GlobalScope.launch(Dispatchers.Main) {
            list.addAll(list)
            notifyDataSetChanged()
        }
    }

    private val runnable: Runnable = Runnable {
        list.addAll(list)
        notifyDataSetChanged()
    }
}