package com.swensun.func.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.item_recycler_view.view.*
import kotlinx.android.synthetic.main.recycler_view_fragment.*

class RecyclerViewFragment : Fragment() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private val adapter = R2Adapter()
    private lateinit var viewModel: RecyclerViewViewModel
    private val datas = arrayListOf<RItem>()
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.recycler_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecyclerViewViewModel::class.java)
        initView()
    }

    private fun initView() {
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        adapter.setLoadMoreListener {
            loadMoreData()
        }
        loadMoreData()
    }

    private fun loadMoreData() {
        (0 until 10).forEach {
            datas.add(RItem().apply { id = index++ })
        }
        adapter.updateList(datas)
    }


    class RItem {
        var id = 0
    }

    inner class RViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
    ) {

        fun setup(item: RItem) {
            itemView.tv_id.text = item.id.toString()
        }

    }

//    inner class RAdapter : ListAdapter<RItem, RViewHolder>(DiffItemCallback()) {
//
//        val itemList = arrayListOf<RItem>()
//        private var loadMore: (() -> Unit)? = null
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
//            return RViewHolder(parent)
//        }
//
//        override fun onBindViewHolder(holder: RViewHolder, position: Int) {
//
//            if (position == itemList.size - 1) {
//                //下拉刷新
//                loadMore?.invoke()
//            }
//            holder.setup(getItem(position))
//
//        }
//
//        fun updateList(list: List<RItem>) {
//            itemList.clear()
//            itemList.addAll(list)
//            submitList(itemList.toList())
//        }
//
//        fun setLoadMoreListener(loadMore: (() -> Unit)?) {
//            this.loadMore = loadMore
//        }
//    }

    class DiffItemCallback : DiffUtil.ItemCallback<RItem>() {
        override fun areItemsTheSame(oldItem: RItem, newItem: RItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RItem, newItem: RItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class R2Adapter : RecyclerView.Adapter<RViewHolder>() {
        override fun getItemCount(): Int {
            return itemList.size
        }

        val itemList = arrayListOf<RItem>()
        private var loadMore: (() -> Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
            return RViewHolder(parent)
        }

        override fun onBindViewHolder(holder: RViewHolder, position: Int) {
            Logger.d("onbind ---")
            if (position == itemList.size - 1) {
                //下拉刷新
                loadMore?.invoke()
            }
            holder.setup(itemList[position])
        }

        fun updateList(list: List<RItem>) {
            recycler_view.post {
//                val callback = DiffCallback(itemList, list)
//                val result = DiffUtil.calculateDiff(callback)
//                result.dispatchUpdatesTo(adapter)
                itemList.clear()
                itemList.addAll(list)
                notifyDataSetChanged()
            }

        }

        fun setLoadMoreListener(loadMore: (() -> Unit)?) {
            this.loadMore = loadMore
        }
    }

    class DiffCallback(ol: List<RItem>, nl: List<RItem>) : DiffUtil.Callback() {


        val oldList = arrayListOf<RItem>()
        val newList = arrayListOf<RItem>()

        init {
            oldList.clear()
            newList.clear()
            oldList.addAll(ol)
            newList.addAll(nl)
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }
    }
}
