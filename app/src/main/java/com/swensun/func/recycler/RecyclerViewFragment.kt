package com.swensun.func.recycler

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.drakeet.multitype.ItemViewDelegate
import com.drakeet.multitype.MultiTypeAdapter
import com.swensun.potato.R
import kotlinx.android.synthetic.main.item_recycler_view.view.*
import kotlinx.android.synthetic.main.load_more.view.*
import kotlinx.android.synthetic.main.recycler_view_fragment.*


class RecyclerViewFragment : Fragment() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private lateinit var viewModel: RecyclerViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.recycler_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        initView()
    }

    private fun initView() {
//        recycler_view.closeDefaultAnimator()
        recycler_view.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager


        /**
         * 普通Adapter
         */
//        val adapter = RAdapter()
//        recycler_view.adapter = adapter
//        adapter.setCountListener { position, count ->
//            Logger.d("${adapter.currentList.map { it.count }}")
//            adapter.notifyDataSetChanged()
//        }
//        adapter.submitList((100 until 103).map {
//            RInt(it)
//        })
//
//        btn_refresh.setOnClickListener {
//            adapter.currentList.add(1, RInt(100 - adapter.itemCount))
//            adapter.notifyDataSetChanged()
//        }
        

        /**
         * MultiTypeAdapter
         */
        val adapter = DiffMultiAdapter()
        recycler_view.adapter = adapter
        adapter.register(RViewHolderDelegate().apply {
            setCountListener { position, count ->
                // 1.diffutil
//                val items = adapterItems.toMutableList()
//                items[position].let {
//                    if (it is RInt) {
//                        val rint = it.clone()
//                        rint.count = count + 1
//                        items[position] = rint
//                        adapter.submitList(items)
//                    }
//                }
                // 2
                adapterItems[position].let {
                    if (it is RInt) {
                        it.count = count + 1
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
        adapter.register(LoadMoreDelegate {
            val items = arrayListOf<Any>().apply {
                addAll(adapter.items)
            }
//            if (items.isNotEmpty()) {
//                items.removeLast()
//            }
            if (items.size < 50) {
                items.addAll((items.size until items.size + 10).map { RInt(it) })
                items.add(LoadMore())
            } else {
                items.add(LoadMore(false))
            }
            adapter.items = items
            recycler_view.post {
                adapter.notifyDataSetChanged()
            }
        })

        adapter.submitList((0 until 10).map { RInt(it) })

        btn_refresh.setOnClickListener {
            val items = adapter.items.toMutableList()
            items.add(0, RInt(items.size))
            adapter.items = items
            adapter.notifyItemInserted(0)
        }
    }
}


class RAdapter : RecyclerView.Adapter<RViewHolder>() {

    var currentList: ArrayList<RInt> = arrayListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        return RViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.setup(currentList[position], countListener)
    }


    private var countListener: ((position: Int, count: Int) -> Unit)? = null
    fun setCountListener(function: (position: Int, count: Int) -> Unit) {
        this.countListener = function
    }

    fun submitList(list: List<RInt>) {
        currentList.clear()
        currentList.addAll(list)
        notifyDataSetChanged()
    }

}

class RViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
) {

    fun setup(
        item: RInt,
        countListener: ((position: Int, count: Int) -> Unit)?
    ) {
        itemView.tv_id.text = "${item.count}"
        itemView.setOnClickListener {
            countListener?.invoke(adapterPosition, item.count)
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

class RViewHolderDelegate : ItemViewDelegate<RInt, RViewHolder>() {

    private var countListener: ((position: Int, count: Int) -> Unit)? = null
    fun setCountListener(function: (position: Int, count: Int) -> Unit) {
        this.countListener = function
    }

    override fun onBindViewHolder(holder: RViewHolder, item: RInt) {
        holder.setup(item, countListener)
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): RViewHolder {
        return RViewHolder(parent)
    }

}

class LoadMoreDelegate(private val block: () -> Unit) :
    ItemViewDelegate<LoadMore, LoadMoreDelegate.LoadMoreViewHolder>() {

    inner class LoadMoreViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.load_more, parent, false)
    ) {
        fun setup(item: LoadMore) {
            if (item.hasMore) {
                itemView.tv_more.text = "....."
            } else {
                itemView.tv_more.text = "到底了"
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): LoadMoreViewHolder {
        return LoadMoreViewHolder(parent)
    }

    override fun onBindViewHolder(holder: LoadMoreViewHolder, item: LoadMore) {
        holder.setup(item)
        if (item.hasMore) {
            block.invoke()
        }
    }
}

class LoadMore(val hasMore: Boolean = true) {
    var type = 0
}

class RIntCallback(val oldItems: List<Any>, val newItems: List<Any>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        when {
            old is RInt && new is RInt -> {
                return old.id == new.id
            }
        }
        return false
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        when {
            old is RInt && new is RInt -> {
                return old.count == new.count
            }
        }
        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return newItems[newItemPosition]
    }
}

class DiffMultiAdapter : MultiTypeAdapter() {

    fun submitList(newItems: List<Any>) {
        val callback = RIntCallback(items, newItems)
        val result = DiffUtil.calculateDiff(callback)
        items = newItems
        result.dispatchUpdatesTo(this)
    }
}

fun RecyclerView.closeDefaultAnimator() {
    if (itemAnimator is SimpleItemAnimator) {
        (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }
}

