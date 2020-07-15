package com.swensun.func.recycler

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.blankj.utilcode.util.ScreenUtils
import com.swensun.func.customview.CustomViewActivity
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.getColor
import com.swensun.swutils.ui.getWinWidth
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.item_recycler_view.view.*
import kotlinx.android.synthetic.main.recycler_view_fragment.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


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
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        recyclerView.smoothScrollBy(0, -dp2px(300f))
                    }
                    if (!recyclerView.canScrollVertically(-1)) {
                        recyclerView.smoothScrollBy(0, dp2px(300f))
                    }
                }
            }
        })
        loadMoreData()
    }

    private fun loadMoreData() {
        (0 until 30).forEach {
            dataList.add(it)
        }
        adapter.notifyDataSetChanged()
    }

    inner class RAdapter : RecyclerView.Adapter<RViewHolder>() {

        private var loadMore: (() -> Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
            val viewHolder = RViewHolder(parent)
            return viewHolder
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: RViewHolder, position: Int) {
            holder.setup()
        }

        fun setLoadMoreListener(function: () -> Unit) {
            this.loadMore = function
        }
    }

    inner class RViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
    ) {

        fun setup() {
            itemView.tv_id.text = "$adapterPosition"
        }
    }
}
