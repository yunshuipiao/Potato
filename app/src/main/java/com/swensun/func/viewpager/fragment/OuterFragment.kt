package com.swensun.func.viewpager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.getWinWidth
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.fragment_outer.*
import kotlinx.android.synthetic.main.h_item.view.*


class OuterFragment : BaseFragment() {

    var adapter: HAdapter = HAdapter()
    private var vid = ""

    companion object {
        fun newInstance(id: String): OuterFragment {
            val fragment = OuterFragment()
            fragment.arguments = Bundle().apply {
                putString("id", id)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vid = arguments?.getString("id") ?: ""
        return inflater.inflate(R.layout.fragment_outer, container, false)
    }

    override fun loadData() {
        recycler_view.layoutManager =
            object : GridLayoutManager(context, 3) {}
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(GridSpacingItemDecoration())
        adapter.submitList((0 until 100).map { "$it" })
//        childFragmentManager.let {
//            adapter = ViewPagerAdapter(it)
//            viewpager.adapter = adapter
//            tab_layout.setupWithViewPager(viewpager)
//            val fragmentList = arrayListOf<Fragment>()
//            val titleList = arrayListOf<String>()
//            (0 until 10).forEach {
//                fragmentList.add(
//                    InnerFragment.newInstance("${vid}-$it")
//                )
//                titleList.add("${vid}-$it")
//                viewpager.offscreenPageLimit = fragmentList.size - 1
//            }
//            adapter.setup(fragmentList, titleList)
//        }
//        Logger.d("id--: $vid")
    }
}

class HAdapter : ListAdapter<String, HAdapter.HViewHolder>(HDiffCallback()) {

    class HViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.h_item, parent, false)
    ) {
        fun setup(item: String?) {
            itemView.tv_number.text = " - $adapterPosition -"
        }
    }


    class HDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HViewHolder {
        return HViewHolder(parent)
    }

    override fun onBindViewHolder(holder: HViewHolder, position: Int) {
        Logger.d("onBindViewHolder, $position")
        holder.setup(getItem(position))
    }
}


