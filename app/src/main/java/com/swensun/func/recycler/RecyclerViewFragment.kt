package com.swensun.func.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.item_recycler_view.view.*
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
        recycler_view.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager


        val adapter = RAdapter()
        recycler_view.adapter = adapter
        adapter.setCountListener { position, count ->
            Logger.d("${adapter.currentList.map { it.count }}")
            adapter.notifyDataSetChanged()
        }
        adapter.submitList((0 until 3).map {
            RInt(it)
        })
    }
}

class RAdapter : ListAdapter<RInt, RViewHolder>(RCallback()) {
    private var countListener: ((position: Int, count: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        return RViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.setup(getItem(position), countListener)
    }

    fun setCountListener(function: (position: Int, count: Int) -> Unit) {
        this.countListener = function
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
            item.count += 1
            countListener?.invoke(adapterPosition, item.count)
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

