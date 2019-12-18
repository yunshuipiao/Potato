package com.swensun.potato.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swensun.potato.R
import com.swensun.potato.SharedViewModelFactory
import com.swensun.potato.UserViewModel
import kotlinx.android.synthetic.main.recycler_view_fragment.*

class RecyclerViewFragment : Fragment() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private var index = 0

    private lateinit var recyclerViewViewModel: RecyclerViewViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.recycler_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewViewModel = ViewModelProviders.of(this).get(RecyclerViewViewModel::class.java)
        userViewModel = ViewModelProviders.of(this, SharedViewModelFactory).get(UserViewModel::class.java)
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
//            numList = numList.mapIndexed { index, s ->
//                  if (index % 2 == 1) {
//                      s + "1"
//                  } else {
//                      s
//                  }
//            } as ArrayList<String>
//            adapter.submitList(numList)
            userViewModel.changeName("#-- $index --")
            index++
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
