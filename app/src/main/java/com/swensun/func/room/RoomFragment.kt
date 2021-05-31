package com.swensun.func.room

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.swensun.base.BaseFragment
import com.swensun.func.room.database.RoomEntity
import com.swensun.potato.databinding.RoomEntityItemBinding
import com.swensun.potato.databinding.RoomFragmentBinding
import com.swensun.swutils.multitype.ViewBindingDelegate
import com.swensun.swutils.multitype.ViewBindingViewHolder

class RoomFragment : BaseFragment<RoomFragmentBinding>() {

    companion object {
        fun newInstance() = RoomFragment()
    }


    private lateinit var viewModel: RoomViewModel
    private var adapter = MultiTypeAdapter()


    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        adapter.register(RoomEntityDelegate().apply {
            buttonClickListener = {
            }
        })
        viewModel.allRoomLiveData.observe(requireActivity(), Observer {
            adapter.items = it.sortedByDescending { it.startTime }
            adapter.notifyDataSetChanged()
        })
        binding.btnAdd.setOnClickListener {
            viewModel.upsertList(RoomEntity())
        }
        binding.btnAddMul.setOnClickListener {
            val entities = (0 until 1000).map {
                RoomEntity().apply { title = "$it" }
            }
            viewModel.upsertList(entities)
        }
        binding.btnClear.setOnClickListener {
            viewModel.allRoom.forEach {
                viewModel.delete(it)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        initView()
    }
}

//class RoomEntityDelegate : ItemViewDelegate<RoomEntity, RoomEntityDelegate.RoomEntityViewHolder>() {
//    var buttonClickListener: ((RoomEntity) -> Unit)? = null
//
//    override fun onBindViewHolder(holder: RoomEntityViewHolder, item: RoomEntity) {
//        holder.setup(item)
//    }
//
//    override fun onCreateViewHolder(context: Context, parent: ViewGroup): RoomEntityViewHolder {
//        return RoomEntityViewHolder(parent)
//    }
//
//    inner class RoomEntityViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
//        LayoutInflater.from(parent.context).inflate(R.layout.room_entity_item, parent, false)
//    ) {
//        fun setup(item: RoomEntity) {
//            itemView.btn_info.text = "${item.id} - ${item.title} - ${item.count}"
//            itemView.btn_info.setOnClickListener {
//                buttonClickListener?.invoke(item)
//            }
//        }
//    }
//}
class RoomEntityDelegate : ViewBindingDelegate<RoomEntity, RoomEntityItemBinding>() {

    var buttonClickListener: ((RoomEntity) -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewBindingViewHolder<RoomEntityItemBinding>,
        item: RoomEntity
    ) {
        holder.binding.btnInfo.text = "${item.id} - ${item.title} - ${item.count}"
        holder.binding.btnInfo.setOnClickListener {
            buttonClickListener?.invoke(item)
        }
    }

}


