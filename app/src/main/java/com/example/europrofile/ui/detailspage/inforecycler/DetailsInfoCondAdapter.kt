package com.example.europrofile.ui.detailspage.inforecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R

class DetailsInfoCondAdapter(val list : LinkedHashMap<String, String>): RecyclerView.Adapter<DetailsInfoCondAdapter.DetailsInfoCondVH>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsInfoCondVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DetailsInfoCondVH(layoutInflater.inflate(R.layout.detailspage_conditioner_vh, parent, false))
    }

    override fun onBindViewHolder(holder: DetailsInfoCondVH, position: Int) {
        holder.key.text = list.keys.elementAt(position)
        holder.infValue.text = list[holder.key.text]
    }

    override fun getItemCount(): Int = list.size

    inner class DetailsInfoCondVH(view: View): RecyclerView.ViewHolder(view){
        val key = view.findViewById<TextView>(R.id.title_info)
        val infValue = view.findViewById<TextView>(R.id.value_info)
    }

}