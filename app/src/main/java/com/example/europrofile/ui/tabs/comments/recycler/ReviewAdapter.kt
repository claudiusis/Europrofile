package com.example.europrofile.ui.tabs.comments.recycler

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.europrofile.R
import com.example.europrofile.core.Variants
import java.util.Locale

class ReviewAdapter(private val listOfReview: ArrayList<ViewReview> = arrayListOf(), private val likeBtn: (type: Variants, reviewId: String) -> Unit,
    private val check : (review : ReviewVH, data : ViewReview) -> Unit): RecyclerView.Adapter<ReviewAdapter.ReviewVH>() {

    fun changeList(newList: List<ViewReview>){
/*        val callbck = RecyclerDiffUtil<ViewReview>(
            old=listOfReview, new=newList,
            {old, new -> old.id == new.id },
            {old, new ->  old.idOfUser == new.idOfUser && old.listOfUserDisLikes==new.listOfUserDisLikes
                    && old.listOfUserLikes==new.listOfUserLikes}
        )
        listOfReview.clear()
        listOfReview.addAll(newList)
        val result = DiffUtil.calculateDiff(callbck)
        result.dispatchUpdatesTo(this)*/
        listOfReview.clear()
        listOfReview.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewVH {
        val inflater = LayoutInflater.from(parent.context)
        return ReviewVH(inflater.inflate(R.layout.review_vh, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewVH, position: Int) {
        holder.onBind(listOfReview[position])
    }

    override fun getItemCount(): Int = listOfReview.size

    inner class ReviewVH(val view: View): RecyclerView.ViewHolder(view) {
        private val accountName: TextView = view.findViewById(R.id.review_owner)
        private val avatar : ImageView = view.findViewById(R.id.basic_review_avatar)
        private val dateReview: TextView = view.findViewById(R.id.review_date)
        val recyclerView: RecyclerView = view.findViewById(R.id.review_images)
        private val description: TextView = view.findViewById(R.id.review_description)
        val likeAmount: TextView = view.findViewById(R.id.like_review_count)
        val dislikeAmount: TextView = view.findViewById(R.id.disLike_review_count)
        val likeImg: ImageView = view.findViewById(R.id.like_icon)
        val dislikeImg: ImageView = view.findViewById(R.id.dislike_icon)
        val likeBlock : LinearLayout = view.findViewById(R.id.like_container)
        val disLikeBlock : LinearLayout = view.findViewById(R.id.dislike_container)

        fun onBind(item: ViewReview) {
            val dateTimeFormatter = java.text.SimpleDateFormat("hh:MM, dd.MM.yyyy", Locale.US)
            accountName.text = item.nameOfUser
            item.userAvatar.let {
                Glide.with(this.itemView.context).load(item.userAvatar)
                    .placeholder(R.color.blue).error(R.color.light_gray).into(avatar)
            }

            likeBlock.setOnClickListener {
                likeBtn.invoke(Variants.Like, item.id!!)
                check.invoke(this, item)
            }

            disLikeBlock.setOnClickListener {
                likeBtn.invoke(Variants.Dislike, item.id!!)
                check.invoke(this, item)
            }

            dateReview.text = item.date?.let { dateTimeFormatter.format(it) }
            description.text = item.description
            likeAmount.text = item.listOfUserLikes.size.toString()
            dislikeAmount.text = item.listOfUserDisLikes.size.toString()

            val uriString = mutableListOf<Uri>()
            item.imageList.forEach { uriString.add(Uri.parse(it)) }

            val adapter = ReviewImgAdapterShow(uriString)

            recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter

            check.invoke(this, item)
        }

    }
}