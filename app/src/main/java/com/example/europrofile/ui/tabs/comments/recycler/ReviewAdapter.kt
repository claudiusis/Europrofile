package com.example.europrofile.ui.tabs.comments.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R
import java.util.Locale

class ReviewAdapter(val listOfReview: List<Review>): RecyclerView.Adapter<ReviewAdapter.ReviewVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewVH {
        val inflater = LayoutInflater.from(parent.context)
        return ReviewVH(inflater.inflate(R.layout.review_vh, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewVH, position: Int) {
        holder.onBind(listOfReview[position])
    }

    override fun getItemCount(): Int = listOfReview.size

    inner class ReviewVH(val view: View): RecyclerView.ViewHolder(view) {
        val accountName: TextView = view.findViewById(R.id.review_owner)
        val dateReview: TextView = view.findViewById(R.id.review_date)
        val recyclerView: RecyclerView = view.findViewById(R.id.review_rv)
        val description: TextView = view.findViewById(R.id.review_description)
        val likeAmount: TextView = view.findViewById(R.id.like_review_count)
        val dislikeAmount: TextView = view.findViewById(R.id.disLike_review_count)

        fun onBind(item: Review) {
            val dateTimeFormatter = java.text.SimpleDateFormat("hh:MM, dd.MM.yyyy", Locale.US)
            accountName.text = item.nameOfUser
            dateReview.text = dateTimeFormatter.format(item.date)
            description.text = item.description
            likeAmount.text = item.listOfUserLikes.size.toString()
            dislikeAmount.text = item.listOfUserDisLikes.size.toString()
          /*  val adapter = ReviewImgAdapter(item.imageList)
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapter*/
        }
    }
}