package com.swensun.func.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.drakeet.multitype.MultiTypeAdapter
import com.swensun.potato.databinding.ItemRecyclerViewBinding
import com.swensun.potato.databinding.RecyclerViewFragmentBinding
import com.ziipin.social.base.multitype.ViewBindingDelegate
import com.ziipin.social.base.multitype.ViewBindingViewHolder
import kotlinx.android.synthetic.main.recycler_view_fragment.*


class RecyclerViewFragment : Fragment() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private lateinit var viewModel: RecyclerViewViewModel
    private lateinit var binding: RecyclerViewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecyclerViewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        initView()
    }

    private fun initView() {
//        recycler_view.closeDefaultAnimator()
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        /**
         * MultiTypeAdapter
         */
        val adapter = MultiTypeAdapter()
        recycler_view.adapter = adapter
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
        btn_refresh.setOnClickListener {

        }
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

