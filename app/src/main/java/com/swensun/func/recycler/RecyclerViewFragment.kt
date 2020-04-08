package com.swensun.func.recycler

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.blankj.utilcode.util.ScreenUtils
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.getWinWidth
import kotlinx.android.synthetic.main.item_recycler_view.view.*
import kotlinx.android.synthetic.main.recycler_view_fragment.*


class RecyclerViewFragment : Fragment() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private val adapter = RAdapter()
    private lateinit var viewModel: RecyclerViewViewModel
    private var index = 0
    private var dataList = arrayListOf<Int>()

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
        recycler_view.layoutManager =
            object: LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, true) {
//                override fun calculateExtraLayoutSpace(
//                    state: RecyclerView.State,
//                    extraLayoutSpace: IntArray
//                ) {
//                    extraLayoutSpace[0] = 1000
//                    extraLayoutSpace[1] = 1000
//                    super.calculateExtraLayoutSpace(state, extraLayoutSpace)
//                }

                override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
                    return getWinWidth()
                }
            }
//        adapter.setLoadMoreListener {
//            loadMoreData()
//        }
        recycler_view.setChildDrawingOrderCallback { childCount, i ->
            childCount - i - 1
        }
        recycler_view.addItemDecoration(RItemDecoration())
        LeftLinearSnapHelper().attachToRecyclerView(recycler_view)
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val childCount = recyclerView.childCount
                (0 until childCount).forEach {
                    val child = recyclerView.getChildAt(it)
                    var right = getWinWidth() - child.right
                    if (right < 0) {
                        right = 0
                    }
                    val scale = right / getWinWidth().toFloat()
                    child.scaleY = 1 - scale * 0.1f
                    child.scaleX = 1 - scale * 0.1f
                    child.translationX = scale * dp2px(100f)
                }
            }
        })
        recycler_view.addOnScrollListener(object: OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recycler_view.layoutManager as LinearLayoutManager
                val first = layoutManager.findFirstVisibleItemPosition()
                val last = layoutManager.findLastVisibleItemPosition()
                (first..last).forEach {
                    var str = "position: $it "
                    recyclerView.findViewHolderForLayoutPosition(it)?.itemView?.let {
                        str += "left: ${it.left}, right:${it.right} "
                        it.tv_id?.text = str
                    }
                }
            }
        })
        loadMoreData()
    }

    private fun loadMoreData() {
        (0 until 10).forEach {
            dataList.add(it)
        }
        adapter.notifyDataSetChanged()
    }

    inner class RViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
    ) {

        init {
            val lp = itemView.layoutParams
            lp.width = (ScreenUtils.getScreenWidth() / 2.3).toInt()
            itemView.layoutParams = lp
        }

        fun setup() {
            itemView.tv_id.text = dataList.getOrNull(adapterPosition).toString()
        }
    }

    inner class RAdapter : RecyclerView.Adapter<RViewHolder>() {

        private var loadMore: (() -> Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
            return RViewHolder(parent)
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

    class RItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
        }
    }
}
