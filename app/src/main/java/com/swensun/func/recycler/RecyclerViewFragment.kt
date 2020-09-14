package com.swensun.func.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.drakeet.multitype.ItemViewBinder
import com.drakeet.multitype.MultiTypeAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.swensun.potato.R
import com.swensun.swutils.ui.getWinWidth
import kotlinx.android.synthetic.main.item_recycler_view.view.*
import kotlinx.android.synthetic.main.recycler_view_fragment.*


class RecyclerViewFragment : Fragment() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private val adapter = RAdapter()
    private lateinit var viewModel: RecyclerViewViewModel
    private val column = 2

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

//        val layoutManager = FlexboxLayoutManager(context).apply {
//            flexDirection = FlexDirection.ROW
//            justifyContent = JustifyContent.FLEX_START
//        }
//        val layoutManager = StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL)
        val layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager


        val adapter = MultiTypeAdapter()
        recycler_view.adapter = adapter
        adapter.register(RViewDelegate())
//        loadMoreData()
        val items = arrayListOf<Any>()
        (0..30).map {
            items.add(RInt(it))
        }
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    private fun loadMoreData() {
        val list = (0..30).map { RInt(it) }
        adapter.submitList(list)
    }
}

class RAdapter : ListAdapter<RInt, RViewHolder>(RCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        return RViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.setup(getItem(position))
    }
}

class RViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
) {

    fun setup(item: RInt) {
        itemView.tv_id.text = "${item.id}"
    }
}

class RViewDelegate : ItemViewBinder<RInt, RViewHolder>() {
    override fun onBindViewHolder(holder: RViewHolder, item: RInt) {
        holder.setup(item)
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RViewHolder {
        return RViewHolder(parent)
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

