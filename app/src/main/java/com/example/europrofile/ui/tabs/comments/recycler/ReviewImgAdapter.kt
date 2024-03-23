package com.example.europrofile.ui.tabs.comments.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R

class ReviewImgAdapter(val imgList: ArrayList<ReviewImg>): RecyclerView.Adapter<ReviewImgAdapter.ReviewImgVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewImgAdapter.ReviewImgVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reviewimages_vh, parent, false)
        return ReviewImgVH(view)
    }

    override fun onBindViewHolder(holder: ReviewImgAdapter.ReviewImgVH, position: Int) {
        holder.img.setImageURI(imgList[position].img)
    }

    override fun getItemCount(): Int = imgList.size

    inner class ReviewImgVH(view: View): RecyclerView.ViewHolder(view){
        val img = view.findViewById<ImageView>(R.id.reviewImg)
    }
}