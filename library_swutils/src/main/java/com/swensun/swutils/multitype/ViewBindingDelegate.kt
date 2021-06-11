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
abstract class ViewBindingDelegate<T, VB : ViewBinding>(private val binding: VB? = null) :
    ItemViewDelegate<T, ViewBindingDelegate.ViewBindingViewHolder<VB>>() {

    class ViewBindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): ViewBindingViewHolder<VB> {
        return if (binding == null) {
            ViewBindingViewHolder(inflateBindingWithGeneric(parent))
        } else {
            ViewBindingViewHolder(binding)
        }
    }
}

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

// 带动画的差分更新
fun MultiTypeAdapter.submitList(callback: AnyCallback) {
    val result = DiffUtil.calculateDiff(callback)
    items = callback.newItems
    result.dispatchUpdatesTo(this)
}

// 旧方法更新
fun MultiTypeAdapter.updateItems(items: List<Any>) {
    this.items = items
    notifyDataSetChanged()
}