package com.example.europrofile.ui.tabs.account.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R

class ChildAdapter(val childList: List<ChildItem>): RecyclerView.Adapter<ChildAdapter.ChildVH>() {

    inner class ChildVH(item: View) : RecyclerView.ViewHolder(item){

        private val title = item.findViewById<TextView>(R.id.setting_title)
        private val arrow = item.findViewById<ImageView>(R.id.arrow_img)

        fun onBind(item: ChildItem){
            title.text = item.title
            arrow.setImageResource(item.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_page_child_vh, parent, false)
        return ChildVH(view)
    }

    override fun getItemCount(): Int = childList.size

    override fun onBindViewHolder(holder: ChildVH, position: Int) {
        holder.onBind(childList[position])
    }
}