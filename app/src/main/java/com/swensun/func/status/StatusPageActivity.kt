package com.swensun.func.status

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.dueeeke.videoplayer.util.visible
import com.swensun.StatusEvent
import com.swensun.base.BaseActivity
import com.swensun.func.recycler.RInt
import com.swensun.potato.databinding.ActivityStatusPageBinding
import com.swensun.potato.databinding.ItemRecyclerViewBinding
import com.swensun.swutils.multitype.ViewBindingDelegate
import com.swensun.swutils.multitype.updateItems
import com.swensun.swutils.ui.context
import com.swensun.swutils.util.Logger

class StatusPageActivity : BaseActivity<ActivityStatusPageBinding>() {

    private val viewModel by lazy { ViewModelProvider(this).get(StatusPageViewModel::class.java) }
    val adapter = MultiTypeAdapter()


    override fun initView(savedInstanceState: Bundle?) {

        viewModel.statusLiveData.observe(this, Observer {
            Logger.d("statusLiveData changed: $it")
            when (it) {
                StatusEvent.ERROR -> {
                    binding.statusView.showErrorStatus()
                }
                StatusEvent.LOADING -> {
                    binding.statusView.showLoadingStatus()
                }
                StatusEvent.SUCCESS -> {
                    binding.statusView.removeStatus()
                }
                StatusEvent.EMPTY -> {
                    binding.statusView.showEmptyStatus()
                }
            }
        })

        // 状态切换
        binding.btnError.setOnClickListener {
            viewModel.loadDataWithStatus(StatusEvent.ERROR)
        }
        binding.btnSuccess.setOnClickListener {
            viewModel.loadDataWithStatus(StatusEvent.SUCCESS)
        }
        binding.btnEmpty.setOnClickListener {
            viewModel.loadDataWithStatus(StatusEvent.EMPTY)
        }

        binding.statusView.register(StatusEvent.ERROR, StatusSubView(context).apply {
            binding.tvTitle.text = "加载错误"
            binding.tvContent.visible = false
            binding.tvConfirm.setOnClickListener {
                viewModel.loadDataWithStatus(StatusEvent.SUCCESS)
            }
        })
        binding.statusView.register(StatusEvent.EMPTY, StatusSubView(context).apply {
            binding.tvTitle.text = "内容为空"
            binding.tvContent.visible = false
            binding.tvConfirm.setOnClickListener {
                viewModel.loadDataWithStatus(StatusEvent.SUCCESS)
            }
        })
        binding.statusView.register(StatusEvent.LOADING, StatusSubView(context).apply {
            binding.tvTitle.text = "加载中..."
            binding.tvContent.visible = false
            binding.tvConfirm.visible = false
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.register(RViewHolderDelegate())
        binding.recyclerView.adapter = adapter
        val items =
            (0 until 10).map { RInt(it) }
        adapter.updateItems(items)
        Logger.d("status")
    }
}

class RViewHolderDelegate : ViewBindingDelegate<RInt, ItemRecyclerViewBinding>() {

    var loadMore: (() -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewBindingViewHolder<ItemRecyclerViewBinding>,
        item: RInt
    ) {
        if (holder.bindingAdapterPosition == adapter.itemCount - 1) {
            loadMore?.invoke()
        }
        holder.binding.tvId.text = item.id.toString()
        holder.binding.root.setOnClickListener {
            
        }
    }
}