package com.example.europrofile.ui.tabs.comments.recycler

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.europrofile.R
import com.makeramen.roundedimageview.RoundedImageView

class ReviewImgAdapterShow(private val _list: MutableList<Uri>): RecyclerView.Adapter<ReviewImgAdapterShow.ReviewVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewVH {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.review_image_vh, parent, false)
        return ReviewVH(inflater)
    }

    override fun onBindViewHolder(holder: ReviewVH, position: Int) {

        Glide.with(holder.itemView).load(_list[position]).error(R.color.light_gray).placeholder(R.color.blue).into(holder.img)

    }

    override fun getItemCount(): Int = _list.size

    inner class ReviewVH(view: View): RecyclerView.ViewHolder(view){
        val img: RoundedImageView = view.findViewById(R.id.review_image)
    }
}