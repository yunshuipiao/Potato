package com.swensun.potato.demo

import android.graphics.Rect
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import kotlinx.android.synthetic.main.item_recycler.*
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
        viewModel = ViewModelProviders.of(this).get(RecyclerViewViewModel::class.java)
        initView()
    }

    private fun initView() {
        recycler_view.layoutManager = LinearLayoutManagerWithScrollTop(requireContext())
        var numList = ArrayList<String>()
        (0..50).forEach { num -> numList.add("$num-$num") }
        val adapter = RRAdapter()
        recycler_view.adapter = adapter
        adapter.submitList(numList)
        scroll_view.setOnClickListener {
//            var position = 0
//            try {
//                position = edit_view.text.toString().toInt()
//            } catch (e: Exception) {
//            }
//            val layoutManager = recycler_view.layoutManager as LinearLayoutManager
//            layoutManager.scrollToPositionWithOffset(position, 0)
            numList = numList.mapIndexed { index, s ->
                  if (index % 2 == 1) {
                      s + "1"
                  } else {
                      s
                  }
            } as ArrayList<String>
            adapter.submitList(numList)
        }
    }

    inner class RAdapter :
        RecyclerView.Adapter<TitleViewHolder>() {

        private var numList = arrayListOf<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
            return TitleViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return numList.size
        }

        override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
            holder.setup(numList[position])
        }

        fun setup(list: ArrayList<String>) {
            numList.clear()
            numList.addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class TitleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
    ) {
        val titleView = itemView.findViewById<TextView>(R.id.ir_tv_title)
        fun setup(s: String) {
            titleView.text = s
        }
    }

    inner class RRAdapter: ListAdapter<String, TitleViewHolder>(TitleDiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
            return TitleViewHolder(parent)
        }

        override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
            holder.setup(getItem(position))
        }
    }

    class TitleDiffCallback: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
