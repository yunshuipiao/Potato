package com.swensun.swutils.multitype

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.drakeet.multitype.ItemViewDelegate
import com.drakeet.multitype.MultiTypeAdapter

/**
 * author : zp
 * date : 2021/5/21
 * xjfad_branch
 */
abstract class ViewBindingDelegate<T, VB : ViewBinding> :
    ItemViewDelegate<T, ViewBindingViewHolder<VB>>() {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): ViewBindingViewHolder<VB> {
        return ViewBindingViewHolder(inflateBindingWithGeneric(parent))
    }
}

abstract class ViewBindingDelegate2<T, VB : ViewBinding> :
    ItemViewDelegate<T, ViewBindingViewHolder<VB>>() {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): ViewBindingViewHolder<VB> {
        return ViewBindingViewHolder(binding(parent))
    }

    abstract fun binding(parent: ViewGroup): VB
}

class ViewBindingViewHolder<VB : ViewBinding>(val binding: VB) :
    RecyclerView.ViewHolder(binding.root)


abstract class AnyCallback(val oldItems: List<Any>, val newItems: List<Any>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return areItemsTheSame(oldItem, newItem)

    }

    abstract fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return areContentsTheSame(oldItem, newItem)
    }

    abstract fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean
}

fun MultiTypeAdapter.submitList(callback: AnyCallback) {
    val result = DiffUtil.calculateDiff(callback)
    items = callback.newItems
    result.dispatchUpdatesTo(this)
}

fun MultiTypeAdapter.updateItems(items: List<Any>) {
    this.items = items
    notifyDataSetChanged()
}