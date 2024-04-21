package com.example.europrofile.ui.tabs.comments.recycler

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R
import com.makeramen.roundedimageview.RoundedImageView

class ReviewImgAdapterShow(private val _list: MutableList<Uri>, val loadImg : (context: Context, into: View)-> Unit): RecyclerView.Adapter<ReviewImgAdapterShow.ReviewVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewVH {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.reviewimages_vh, parent, false)
        return ReviewVH(inflater)
    }

    override fun onBindViewHolder(holder: ReviewVH, position: Int) {

        holder.cross.visibility = View.GONE
        holder.layout.layoutParams = ViewGroup.LayoutParams(100, LayoutParams.WRAP_CONTENT)
        holder.img.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        loadImg.invoke(holder.itemView.context, holder.img)

    }

    override fun getItemCount(): Int = _list.size

    inner class ReviewVH(view: View): RecyclerView.ViewHolder(view){
        val img: RoundedImageView = view.findViewById(R.id.review_image)
        val cross : ImageView = view.findViewById(R.id.cancel)
        val layout : ConstraintLayout = view.findViewById(R.id.item_layout)
    }
}