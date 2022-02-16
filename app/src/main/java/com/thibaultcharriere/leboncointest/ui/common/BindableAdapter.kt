package com.thibaultcharriere.leboncointest.ui.common

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BindableAdapter<T, VH : BindableViewHolder<T>> : RecyclerView.Adapter<VH>() {

    val listItems = mutableListOf<T>()

    open fun setData(newList: List<T>) {
        listItems.clear()
        listItems.addAll(newList)
    }
    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(listItems[position])
    }
}

abstract class BindableViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}

class BindableDiffCallback<T>(
    private val old: List<T>,
    private val new: List<T>,
    private val areItemsSame: (old: T, new: T) -> Boolean,
    private val areContentsSame: (old: T, new: T) -> Boolean
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = areItemsSame(old[oldItemPosition], new[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areContentsSame(old[oldItemPosition], new[newItemPosition])
}
