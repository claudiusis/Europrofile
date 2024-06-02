package com.example.europrofile.core.ui

import androidx.recyclerview.widget.DiffUtil

class RecyclerDiffUtil<T>(val old: List<T>, val new: List<T>,
    private val areItemsTheSameImpl: (old: T, new: T)->Boolean = {oldItem, newItem -> oldItem==newItem},
    private val areContentsTheSameImpl: (old: T, new: T)->Boolean = {oldItem, newItem -> oldItem==newItem}
    ): DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val itemOld = old[oldItemPosition]
        val itemNew = new[newItemPosition]
        return areItemsTheSameImpl(itemOld, itemNew)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val itemOld = old[oldItemPosition]
        val itemNew = new[newItemPosition]
        return areContentsTheSameImpl(itemOld, itemNew)
    }
}