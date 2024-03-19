package com.example.europrofile.ui.tabs.comments.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(val listOfReview: List<Review>): RecyclerView.Adapter<ReviewAdapter.ReviewVH>() {



    inner class ReviewVH(view: View): RecyclerView.ViewHolder(view) {
    }
}