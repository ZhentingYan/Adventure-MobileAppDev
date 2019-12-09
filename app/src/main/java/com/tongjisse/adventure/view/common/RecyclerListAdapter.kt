package com.tongjisse.adventure.view.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

open class RecyclerListAdapter(
        var items: List<AnyItemAdapter> = listOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position].bindViewHolder(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return items.first { it.layoutId == viewType }.onCreateViewHolder(itemView)

    }

    override fun getItemViewType(position: Int): Int {
        return items[position].layoutId

    }
}
typealias  AnyItemAdapter = ItemAdapter //定义别名
<out RecyclerView.ViewHolder>