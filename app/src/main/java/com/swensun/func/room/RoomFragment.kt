package com.swensun.func.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.swensun.func.room.database.RoomEntity
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.item_room.view.*
import kotlinx.android.synthetic.main.room_fragment.*
import org.w3c.dom.Entity

class RoomFragment : Fragment() {

    companion object {
        fun newInstance() = RoomFragment()
    }

    var index = 0

    private val adapter = RoomAdapter()
    private lateinit var viewModel: RoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.room_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        initView()
    }

    private fun initView() {

        recycler_view.layoutManager = GridLayoutManager(context, 1)
        recycler_view.setHasFixedSize(true)
        adapter.clickListener = object : (Int) -> Unit {
            override fun invoke(position: Int) {
                val list = adapter.currentList
                val entity = list.getOrNull(position)
                entity?.let {
                    val newEntity = RoomEntity().apply {
                        id = it.id
                        title = "${it.id + 1}"
                    }
                    val newL =
                        list.mapIndexed { index, roomEntity -> if (index == position) newEntity else roomEntity }
                    adapter.submitList(newL)
                }
            }
        }
        recycler_view.adapter = adapter

        btn_add.setOnClickListener {
            viewModel.add(getEditContent())
        }
        btn_update.setOnClickListener {
            viewModel.update(getEditContent())
        }
        btn_delete.setOnClickListener {
            viewModel.delete(getEditContent())
        }
        viewModel.roomQueryLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        refresh_view.setOnRefreshListener {
            (1 until 3).forEach {
                viewModel.add(adapter.itemCount + it)
            }
            refresh_view.isRefreshing = false
        }
    }

    private fun getEditContent(): Int {
        val num = et_content.text.toString()
        return try {
            num.toInt()
        } catch (e: Exception) {
            0
        }
    }
}


class RoomAdapter : ListAdapter<RoomEntity, RoomAdapter.RoomViewHolder>(RoomCallBack()) {

    var clickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.setup(getItem(position))
    }

    override fun onBindViewHolder(
        holder: RoomViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val any = payloads.getOrNull(0)
            if (any is RoomEntity) {
                holder.itemView.tv_title.text = any.title
            }
        }
    }

    inner class RoomViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
    ) {
        fun setup(item: RoomEntity?) {
            item?.let {
                itemView.tv_title.text = item.title
                itemView.tv_count.text = item.id.toString()
            }

            itemView.setOnClickListener {
                clickListener?.invoke(adapterPosition)
            }
        }
    }
}

class RoomCallBack : DiffUtil.ItemCallback<RoomEntity>() {
    override fun areContentsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean {
        var result = oldItem.title == newItem.title
        return result
    }

    override fun areItemsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean {
        val result = oldItem.id == newItem.id
        return result
    }

    override fun getChangePayload(oldItem: RoomEntity, newItem: RoomEntity): Any? {
        return newItem
    }
}
