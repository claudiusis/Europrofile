package com.example.europrofile.ui.tabs.main.newsrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R
import com.makeramen.roundedimageview.RoundedImageView

class ExamplesAdapter(val list: List<Image>): RecyclerView.Adapter<ExamplesAdapter.ExampleVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExampleVH(layoutInflater.inflate(R.layout.example_vh, parent, false))
    }

    override fun onBindViewHolder(holder: ExampleVH, position: Int) {
        holder.img.setImageResource(list[position].image)
    }

    override fun getItemCount(): Int = list.size

    inner class ExampleVH(view: View): RecyclerView.ViewHolder(view){
        val img = view.findViewById<RoundedImageView>(R.id.imageView)
    }
}