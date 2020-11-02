package com.swensun.func.room

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.drakeet.multitype.MultiTypeAdapter
import com.swensun.func.room.database.RoomEntity
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.room_entity_item.view.*
import kotlinx.android.synthetic.main.room_fragment.*
import kotlinx.android.synthetic.main.room_fragment.view.*

class RoomFragment : Fragment() {

    companion object {
        fun newInstance() = RoomFragment()
    }


    private lateinit var viewModel: RoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.room_fragment, container, false)
    }

    private var adapter = MultiTypeAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        initView()
    }

    private fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.adapter = adapter
        adapter.register(RoomEntityDelegate().apply {
            buttonClickListener = {
                it.count += 1
                viewModel.upsert(it)
            }
        })
        viewModel.allRoomLiveData.observe(requireActivity(), Observer {
            Logger.d("room res: ${it.map { it.id }}")
            adapter.items = it.sortedByDescending { it.startTime }
            adapter.notifyDataSetChanged()
        })
        btn_add.setOnClickListener {
            (0..10).forEach {
                viewModel.addRoomEntity()
            }
        }
        btn_clear.setOnClickListener {
            viewModel.allRoom.forEach {
                viewModel.deleta(it)
            }
        }
    }
}

class RoomEntityDelegate : ItemViewDelegate<RoomEntity, RoomEntityDelegate.RoomEntityViewHolder>() {
    var buttonClickListener: ((RoomEntity) -> Unit)? = null

    override fun onBindViewHolder(holder: RoomEntityViewHolder, item: RoomEntity) {
        holder.setup(item)
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): RoomEntityViewHolder {
        return RoomEntityViewHolder(parent)
    }

    inner class RoomEntityViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.room_entity_item, parent, false)
    ) {
        fun setup(item: RoomEntity) {
            itemView.btn_info.text = "${item.id} - ${item.title} - ${item.count}"
            itemView.btn_info.setOnClickListener {
                buttonClickListener?.invoke(item)
            }
        }
    }
}


