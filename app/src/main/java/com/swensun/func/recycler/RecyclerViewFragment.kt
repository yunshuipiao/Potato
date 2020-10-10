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
import com.swensun.swutils.util.Logger
import com.swensun.swutils.util.deepClone
import kotlinx.android.synthetic.main.item_recycler_view.view.*
import kotlinx.android.synthetic.main.recycler_view_fragment.*
import org.jetbrains.anko.support.v4.toast


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
        recycler_view.setHasFixedSize(true)

//        val layoutManager = FlexboxLayoutManager(context).apply {
//            flexDirection = FlexDirection.ROW
//            justifyContent = JustifyContent.FLEX_START
//        }
//        val layoutManager = StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL)
        val layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager


        val adapter = RAdapter()
        recycler_view.adapter = adapter
        adapter.setCountListener { position, count ->
            Logger.d("${adapter.currentList.map { it.count }}")
//            Logger.d("---- start ------")
//            val start = System.currentTimeMillis()
//            var list = adapter.currentList
////            (0 until 20).forEach {
////                adapter.currentList.map { it }
////            }
//            Logger.d("---- end ${System.currentTimeMillis() - start} ------")
//            list[position].count = count + 1
////            adapter.submitList(list)
//            adapter.notifyDataSetChanged()
//            toast("${list}, ${position}, $count")
        }
        adapter.submitList((0 until 3).map {
            RInt(it)
        })

        btn_refresh.setOnClickListener {

        }

    }
}

class RAdapter : ListAdapter<RInt, RAdapter.RViewHolder>(RCallback()) {
    private var countListener: ((position: Int, count: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        return RViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.setup(getItem(position))
    }

    fun setCountListener(function: (position: Int, count: Int) -> Unit) {
        this.countListener = function
    }


    inner class RViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
    ) {

        fun setup(item: RInt) {
            itemView.tv_id.text = "${item.count}"
            itemView.setOnClickListener {
                item.count += 1
                notifyDataSetChanged()
                countListener?.invoke(adapterPosition, item.count)
            }
        }
    }
}


class RCallback : DiffUtil.ItemCallback<RInt>() {
    override fun areItemsTheSame(oldItem: RInt, newItem: RInt): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RInt, newItem: RInt): Boolean {
        return oldItem.count == newItem.count
    }

}

class RInt(val id: Int) {
    var count = id
}

