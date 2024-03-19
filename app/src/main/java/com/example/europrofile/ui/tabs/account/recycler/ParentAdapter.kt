package com.example.europrofile.ui.tabs.account.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R

class ParentAdapter(val list: List<ParentItem>) : RecyclerView.Adapter<ParentAdapter.ParentVH>() {

    inner class ParentVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val title = itemView.findViewById<TextView>(R.id.header_title)
        private val recycler = itemView.findViewById<RecyclerView>(R.id.parent_recycler_acc)

        fun onBind(item: ParentItem){
            title.text = item.title
            recycler.layoutManager = LinearLayoutManager(itemView.context)
            val adapter = ChildAdapter(item.settingList)
            recycler.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentVH {
        val layout = LayoutInflater.from(parent.context)
        return ParentVH(layout.inflate(R.layout.account_page_parent_vh, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ParentVH, position: Int) {
        holder.onBind(list[position])
    }
}