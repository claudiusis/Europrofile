package com.example.europrofile.ui.tabs.main.condition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R

class ConditionParentAdapter(private val listOfCondTypes : ArrayList<CondTypeCard> = arrayListOf(), private val clickFunc : (link: String, imgList : List<String>)->Unit): RecyclerView.Adapter<ConditionParentAdapter.ConditionParentVH>() {

    fun addItems(item : CondTypeCard){
        listOfCondTypes.add(item)
        notifyItemInserted(listOfCondTypes.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionParentVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ConditionParentVH(layoutInflater.inflate(R.layout.cond_parent_vh, parent, false))
    }

    override fun onBindViewHolder(holder: ConditionParentVH, position: Int) {
        holder.title.text = listOfCondTypes[position].title

        holder.recycler.layoutManager = GridLayoutManager(holder.itemView.context, 2)
        holder.recycler.adapter = ConditionChildAdapter(listOfCondTypes[position].condList, clickFunc)
    }

    override fun getItemCount(): Int = listOfCondTypes.size

    inner class ConditionParentVH(view: View): RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.cond_type_title)
        val recycler = view.findViewById<RecyclerView>(R.id.cond_child_recycler)
    }
}