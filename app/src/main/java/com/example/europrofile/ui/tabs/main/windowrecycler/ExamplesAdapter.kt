package com.example.europrofile.ui.tabs.main.windowrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.europrofile.R
import com.example.europrofile.ui.tabs.main.newsrecycler.Image


class ExamplesAdapter: RecyclerView.Adapter<ExamplesAdapter.ExampleVH>() {

    private val list = arrayListOf(
        Image("https://firebasestorage.googleapis.com/v0/b/europrofile-b9677.appspot.com/o/ex_1.jpg?alt=media&token=6ec18528-4d72-4cf8-a454-e58e8d36ba42"),
        Image("https://firebasestorage.googleapis.com/v0/b/europrofile-b9677.appspot.com/o/ex_2.jpg?alt=media&token=5209c426-8fb4-4f85-bd43-bcb34bdd09e5"),
        Image("https://firebasestorage.googleapis.com/v0/b/europrofile-b9677.appspot.com/o/ex_3.jpg?alt=media&token=7fd062db-ba7a-4145-af0a-5c418a96b0f8"),
        Image("https://firebasestorage.googleapis.com/v0/b/europrofile-b9677.appspot.com/o/ex_4.jpg?alt=media&token=d3f1301b-21e8-4c76-8b42-ec57aba5dc2d"),
        Image("https://firebasestorage.googleapis.com/v0/b/europrofile-b9677.appspot.com/o/ex_5.jpg?alt=media&token=e3682aba-773a-43ee-832b-8d1573d9ab7c")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExampleVH(layoutInflater.inflate(com.example.europrofile.R.layout.example_vh, parent, false))

    }

    override fun onBindViewHolder(holder: ExampleVH, position: Int) {

        Glide.with(holder.itemView.context)
            .load(list[position].image)
            .placeholder(R.color.blue).error(R.color.nav_color)
            .into(holder.img)

    }

    override fun getItemCount(): Int = list.size

    inner class ExampleVH(view: View): RecyclerView.ViewHolder(view){
        val img: ImageView = view.findViewById(com.example.europrofile.R.id.imageView)
    }
}



