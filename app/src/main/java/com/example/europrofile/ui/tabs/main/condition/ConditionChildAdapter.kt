package com.example.europrofile.ui.tabs.main.condition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.europrofile.R
import com.example.europrofile.core.ui.RecyclerDiffUtil

class ConditionChildAdapter(private var listOfCondition : List<Conditioner> = listOf(), private val clickFunc : (link: String, imgList : List<String>)->Unit, private val clickLike : (elem : Conditioner)->Unit) : RecyclerView.Adapter<ConditionChildAdapter.ConditionChildVH>()  {

    fun refactorList(newList : List<Conditioner>){
        val callback = RecyclerDiffUtil<Conditioner>(listOfCondition, newList,{
            old, new -> old == new
        }, { old, new -> old.title==new.title })
        listOfCondition = newList
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
    }

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
        private val like = view.findViewById<ImageButton>(R.id.heart_icon)

        fun onBind(item : Conditioner){

            this.itemView.setOnClickListener {
                clickFunc.invoke(item.pageLink, item.img)
            }

            like.setOnClickListener {
                clickLike.invoke(item)
            }


            title.text = item.title
            price.text = item.price+" ₽"

            val imageList = ArrayList<SlideModel>()

            for (img in item.img){
                imageList.add(SlideModel(
                    img
                ))
            }

            images.setImageList(imageList)

        }
    }
}