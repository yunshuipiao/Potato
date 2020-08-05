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
import com.swensun.potato.R
import kotlinx.android.synthetic.main.item_recycler_view.view.*
import kotlinx.android.synthetic.main.recycler_view_fragment.*


class RecyclerViewFragment : Fragment() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private val adapter = RAdapter()
    private lateinit var viewModel: RecyclerViewViewModel
    private var dataList = arrayListOf<Int>()
    val linearLayoutManager by lazy {
        object : LinearLayoutManager(requireContext()) {

        }
    }

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
        recycler_view.layoutManager = linearLayoutManager
        loadMoreData()
        btn_refresh.setOnClickListener {
            val newList =
                adapter.currentList.filterIndexed { index, rInt -> index != 0 }.map { RInt(it.id) }
            adapter.submitList(newList)
        }
    }

    private fun loadMoreData() {
        val list = (0..30).map { RInt(it) }
        adapter.submitList(list)
    }

    inner class RAdapter : ListAdapter<RInt, RViewHolder>(RCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
            return RViewHolder(parent)
        }

        override fun onBindViewHolder(holder: RViewHolder, position: Int) {
            holder.setup(getItem(position))
        }
    }

    inner class RViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
    ) {

        fun setup(item: RInt) {
            itemView.tv_id.text = "${item.id}"
        }
    }
}

class RCallback : DiffUtil.ItemCallback<RInt>() {
    override fun areItemsTheSame(oldItem: RInt, newItem: RInt): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RInt, newItem: RInt): Boolean {
        return oldItem.id == newItem.id
    }

}

class RInt(val id: Int)
