package com.swensun.func.recycler

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.drakeet.multitype.MultiTypeAdapter
import com.swensun.potato.R
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
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        initView()
    }

    private fun initView() {
        recycler_view.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager


        /**
         * 普通Adapter
         */
//        val adapter = RAdapter()
//        recycler_view.adapter = adapter
//        adapter.setCountListener { position, count ->
//            Logger.d("${adapter.currentList.map { it.count }}")
//            adapter.notifyDataSetChanged()
//        }
//        adapter.submitList((100 until 103).map {
//            RInt(it)
//        })
//
//        btn_refresh.setOnClickListener {
//            adapter.currentList.add(1, RInt(100 - adapter.itemCount))
//            adapter.notifyDataSetChanged()
//        }

        /**
         * MultiTypeAdapter
         */
        val adapter = MultiTypeAdapter()
        recycler_view.adapter = adapter
        adapter.register(RViewHolderDelegate().apply {
            setCountListener { position, count ->
                adapter.notifyDataSetChanged()
            }
        })
        adapter.items = (0 until 3).map { RInt(it) }
        adapter.notifyDataSetChanged()

        btn_refresh.setOnClickListener {
            val list = ArrayList(adapter.items)
            list.add(1, RInt(100 - adapter.itemCount))
            adapter.items = list
            adapter.notifyItemInserted(1)
        }
    }
}

class RAdapter : RecyclerView.Adapter<RViewHolder>() {

    var currentList: ArrayList<RInt> = arrayListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        return RViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.setup(currentList[position], countListener)
    }


    private var countListener: ((position: Int, count: Int) -> Unit)? = null
    fun setCountListener(function: (position: Int, count: Int) -> Unit) {
        this.countListener = function
    }

    fun submitList(list: List<RInt>) {
        currentList.clear()
        currentList.addAll(list)
        notifyDataSetChanged()
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

class RInt(val id: Int) {
    var count = id
}

class RViewHolderDelegate : ItemViewDelegate<RInt, RViewHolder>() {

    private var countListener: ((position: Int, count: Int) -> Unit)? = null
    fun setCountListener(function: (position: Int, count: Int) -> Unit) {
        this.countListener = function
    }

    override fun onBindViewHolder(holder: RViewHolder, item: RInt) {
        holder.setup(item, countListener)
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): RViewHolder {
        return RViewHolder(parent)
    }

}

