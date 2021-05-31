package com.swensun.func.recycler

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.drakeet.multitype.MultiTypeAdapter
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.ItemRecyclerViewBinding
import com.swensun.potato.databinding.RecyclerViewFragmentBinding
import com.swensun.swutils.multitype.ViewBindingDelegate
import com.swensun.swutils.multitype.ViewBindingViewHolder


class RecyclerViewFragment : BaseFragment<RecyclerViewFragmentBinding>() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private lateinit var viewModel: RecyclerViewViewModel

    private fun initView() {
//        recycler_view.closeDefaultAnimator()
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        /**
         * MultiTypeAdapter
         */
        val adapter = MultiTypeAdapter()
        binding.recyclerView.adapter = adapter
        adapter.register(RViewHolderDelegate().apply {
            loadMore = {
                val size = adapter.items.size
                val items = arrayListOf<Any>()
                items.addAll(adapter.items)
                (size until size + 10).forEach {
                    items.add(RInt(it))
                }
                binding.recyclerView.post {
                    adapter.submitList2(callback = RIntCallback(adapter.items, items))
                }
            }
        })
        val items = (0 until 10).map { RInt(it) }
        adapter.submitList(items, RIntCallback(adapter.items, items))
        binding.recyclerView.setOnClickListener {

        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        initView()
    }
}

class RInt(val id: Int) {
    var count = id

    fun clone(): RInt {
        val rint = RInt(id)
        rint.count = count
        return rint
    }
}

class RViewHolderDelegate : ViewBindingDelegate<RInt, ItemRecyclerViewBinding>() {

    var loadMore: (() -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewBindingViewHolder<ItemRecyclerViewBinding>,
        item: RInt
    ) {
        if (holder.adapterPosition == adapter.itemCount - 1) {
            loadMore?.invoke()
        }
        holder.binding.tvId.text = item.id.toString()
    }
}

class RIntCallback(oldItems: List<Any>, newItems: List<Any>) : AnyCallback(oldItems, newItems) {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        when {
            oldItem is RInt && newItem is RInt -> {
                return oldItem.id == newItem.id
            }
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        when {
            oldItem is RInt && newItem is RInt -> {
                return oldItem.count == newItem.count
            }
        }
        return false
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

fun MultiTypeAdapter.submitList(newItems: List<Any>, callback: DiffUtil.Callback) {
    val result = DiffUtil.calculateDiff(callback)
    items = newItems
    result.dispatchUpdatesTo(this)
}

fun MultiTypeAdapter.submitList2(callback: AnyCallback) {
    val result = DiffUtil.calculateDiff(callback)
    items = callback.newItems
    result.dispatchUpdatesTo(this)
}

fun RecyclerView.closeDefaultAnimator() {
    if (itemAnimator is SimpleItemAnimator) {
        (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }
}

