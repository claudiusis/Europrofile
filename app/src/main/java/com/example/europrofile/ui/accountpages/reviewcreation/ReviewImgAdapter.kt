package com.example.europrofile.ui.accountpages.reviewcreation

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R
import com.example.europrofile.core.ui.RecyclerDiffUtil
import com.makeramen.roundedimageview.RoundedImageView

class ReviewImgAdapter(): RecyclerView.Adapter<ReviewImgAdapter.ReviewImgVH>() {

    private val _imgList: ArrayList<Uri> = arrayListOf()
    val imgList : List<Uri> = _imgList
    fun addImg(img: Uri){
        val callback = RecyclerDiffUtil(
            _imgList, _imgList+img,
            {old, new ->  old==new}
        )
        val calc = DiffUtil.calculateDiff(callback)
        _imgList.add(img)
        calc.dispatchUpdatesTo(this)
    }
    private fun deleteIMg(position: Int){
        val copyList : ArrayList<Uri> = arrayListOf()
        copyList.addAll(_imgList)
        copyList.removeAt(position)

        val callback = RecyclerDiffUtil(
            _imgList, copyList,
            {old, new ->  old==new}
        )
        val calc = DiffUtil.calculateDiff(callback)
        _imgList.removeAt(position)
        calc.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewImgVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reviewimages_vh, parent, false)
        return ReviewImgVH(view)
    }

    override fun onBindViewHolder(holder: ReviewImgVH, position: Int) {
        holder.img.setImageURI(_imgList[position])

        holder.cancel.setOnClickListener {
            deleteIMg(position)
        }

    }

    override fun getItemCount(): Int = _imgList.size

    inner class ReviewImgVH(view: View): RecyclerView.ViewHolder(view){
        val img: RoundedImageView = view.findViewById<RoundedImageView>(R.id.review_image)
        val cancel : ImageView = view.findViewById(R.id.cancel)
    }
}