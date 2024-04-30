package com.example.europrofile.ui.tabs.main.condition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.example.europrofile.R

class ConditionChildAdapter(private val listOfCondition : List<Conditioner>) : RecyclerView.Adapter<ConditionChildAdapter.ConditionChildVH>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionChildVH {
        val inflater = LayoutInflater.from(parent.context)
        return ConditionChildVH(inflater.inflate(R.layout.cond_child_vh, parent, false))
    }

    override fun onBindViewHolder(holder: ConditionChildVH, position: Int) {
        holder.onBind(listOfCondition[position])
    }

    override fun getItemCount(): Int = listOfCondition.size

    inner class ConditionChildVH(view: View): RecyclerView.ViewHolder(view){
        private val title = view.findViewById<TextView>(R.id.name_of_cond)
        private val price = view.findViewById<TextView>(R.id.cond_price)
        private val images = view.findViewById<ImageSlider>(R.id.slider)

        fun onBind(item : Conditioner){

            title.text = item.title
            price.text = item.price

        }
    }
}