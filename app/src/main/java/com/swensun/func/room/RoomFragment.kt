package com.swensun.func.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swensun.func.room.database.RoomEntity
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.item_room.view.*
import kotlinx.android.synthetic.main.room_fragment.*

class RoomFragment : Fragment() {

    companion object {
        fun newInstance() = RoomFragment()
    }

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
        viewModel = ViewModelProviders.of(this).get(RoomViewModel::class.java)
        initView()
    }

    private fun initView() {
        viewModel.multiRoomLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)
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
        btn_query.setOnClickListener {
            Logger.d("query: ${viewModel.queryRooms()}")
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.setup(getItem(position))
    }

    inner class RoomViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
    ) {
        fun setup(item: RoomEntity?) {
            item?.let {
                itemView.tv_title.text = item.title
            }
        }
    }
}

class RoomCallBack : DiffUtil.ItemCallback<RoomEntity>() {
    override fun areContentsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean {
        return oldItem.id == newItem.id
    }

}
