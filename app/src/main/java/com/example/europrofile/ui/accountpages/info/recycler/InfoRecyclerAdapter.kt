package com.example.europrofile.ui.accountpages.info.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.europrofile.R

class InfoRecyclerAdapter(private val list : List<Info>) : RecyclerView.Adapter<InfoRecyclerAdapter.InfoVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoVH {
        val inflater = LayoutInflater.from(parent.context)
        return InfoVH(inflater.inflate(R.layout.info_vh, parent, false))
    }

    override fun onBindViewHolder(holder: InfoVH, position: Int) {
        Glide.with(holder.itemView).load(list[position].img).error(R.drawable.buttons).placeholder(R.drawable.buttons).into(holder.img)
        holder.text.text = list[position].text
    }

    override fun getItemCount(): Int = list.size

    inner class InfoVH(view: View) :RecyclerView.ViewHolder(view){
        val img = view.findViewById<ImageView>(R.id.img_info)!!
        val text = view.findViewById<TextView>(R.id.text_info)!!
    }
}