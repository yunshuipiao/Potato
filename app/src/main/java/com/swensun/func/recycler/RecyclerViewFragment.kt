package com.swensun.func.recycler

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.drakeet.multitype.MultiTypeAdapter
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.ItemRecyclerViewBinding
import com.swensun.potato.databinding.RecyclerViewFragmentBinding
import com.swensun.swutils.multitype.AnyCallback
import com.swensun.swutils.multitype.ViewBindingDelegate
import com.swensun.swutils.multitype.updateItems
import com.swensun.swutils.ui.toast


class RecyclerViewFragment : BaseFragment<RecyclerViewFragmentBinding>() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private var count = 0

    var clickListener: (() -> Unit)? = null

    private lateinit var viewModel: RecyclerViewViewModel

    private fun initView() {
//        recycler_view.closeDefaultAnimator()
        binding.recyclerView.setHasFixedSize(true)

        val count = arguments?.getInt("count") ?: 10
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        /**
         * MultiTypeAdapter
         */
        val adapter = MultiTypeAdapter()
        adapter.register(RViewHolderDelegate().apply {
//            loadMore = {
//                binding.recyclerView.post {
//                    val items = arrayListOf<Any>()
//                    items.addAll(adapter.items)
//                    items.addAll((0 until 5).map { RInt(it) })
//                    adapter.updateItems(items)
//                }
//            }
        })
        binding.recyclerView.adapter = adapter
        val items =
            (0 until count).map { RInt(it) }
        adapter.updateItems(items)

        binding.refreshView.setOnRefreshListener {
            binding.refreshView.isRefreshing = false
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        initView()
    }

    inner class RViewHolderDelegate : ViewBindingDelegate<RInt, ItemRecyclerViewBinding>() {

        var loadMore: (() -> Unit)? = null

        override fun onBindViewHolder(
            holder: ViewBindingViewHolder<ItemRecyclerViewBinding>,
            item: RInt
        ) {
            if (holder.bindingAdapterPosition == adapter.itemCount - 1) {
                loadMore?.invoke()
            }
            holder.binding.tvId.text = item.id.toString()
            holder.binding.root.setOnClickListener {
                toast("click root")
                clickListener?.invoke()
            }
        }
    }
}

class RInt(val id: Int) {
    var count = id
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

fun RecyclerView.closeDefaultAnimator() {
    if (itemAnimator is SimpleItemAnimator) {
        (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }
}

