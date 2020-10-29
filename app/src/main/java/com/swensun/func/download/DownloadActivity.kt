package com.swensun.func.download

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.drakeet.multitype.MultiTypeAdapter
import com.swensun.potato.R
import com.swensun.swutils.ui.context
import kotlinx.android.synthetic.main.activity_download.*
import kotlinx.android.synthetic.main.progress_item.view.*

class DownloadActivity : AppCompatActivity() {

    val adapter = MultiTypeAdapter()
    val mItems = arrayListOf<ProgressStatus>()

    private val MAX_CACHEING = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        adapter.register(ProgressViewHolderDelegate().apply {
            statusChangedListener = { position, status ->
                updateItems()
            }
        })
        (0 until 10).forEach {
            mItems.add(ProgressStatus())
        }

        updateItems()
    }

    fun updateItems() {
        playStatusChanged()
        val items = mItems.filter { it.status != PlayCacheStatus.CACHED }
        adapter.items = items
        adapter.notifyDataSetChanged()
    }


    fun playStatusChanged() {
        //队列调度系统

        //先处理正在下载
        var cachingItems = mItems.sortedBy { it.updateTime }
            .filter { it.status == PlayCacheStatus.CACHING }
        cachingItems.forEachIndexed { index, progressStatus ->
            if (index >= MAX_CACHEING) {
                progressStatus.status = PlayCacheStatus.PENDING
            }
        }
        var cachingCount = mItems.count { it.status == PlayCacheStatus.CACHING }
        mItems.forEach {
            when (it.status) {
                PlayCacheStatus.PENDING -> {
                    if (cachingCount < MAX_CACHEING) {
                        cachingCount += 1
                        it.status = PlayCacheStatus.CACHING
                    }
                }
                PlayCacheStatus.CACHED -> {

                }
                PlayCacheStatus.CACHING -> {
                }
                PlayCacheStatus.ERROR -> {
                }
                PlayCacheStatus.PAUSED -> {

                }
            }
        }
    }
}

sealed class PlayCacheStatus {
    object PENDING : PlayCacheStatus()
    object CACHING : PlayCacheStatus()
    object PAUSED : PlayCacheStatus()
    object ERROR : PlayCacheStatus()
    object CACHED : PlayCacheStatus()
}

class ProgressViewHolderDelegate :
    ItemViewDelegate<ProgressStatus, ProgressViewHolderDelegate.ProgressViewHolder>() {


    var statusChangedListener: ((position: Int, status: ProgressStatus?) -> Unit)? = null
    var downloadRunnable: Runnable? = null


    inner class ProgressViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.progress_item, parent, false)
    ) {

        private var item: ProgressStatus? = null

        init {

            downloadRunnable = Runnable {
//                item?.let {
//                    it.progress += 1
//                    if (it.progress == 100) {
//                        it.status = PlayCacheStatus.CACHED
//                    }
//                    statusChangedListener?.invoke(adapterPosition, it)
//                }
            }

            itemView.setOnClickListener {
                item?.let {
                    when (it.status) {
                        PlayCacheStatus.PENDING -> {
                            it.status = PlayCacheStatus.CACHING
                        }
                        PlayCacheStatus.CACHED -> {
                        }
                        PlayCacheStatus.CACHING -> {
                            it.status = PlayCacheStatus.PAUSED
                        }
                        PlayCacheStatus.ERROR -> {
                            it.status = PlayCacheStatus.PENDING
                        }
                        PlayCacheStatus.PAUSED -> {
                            it.status = PlayCacheStatus.PENDING
                        }
                    }
                    statusChangedListener?.invoke(adapterPosition, it)

                }
            }
        }

        fun setup(item: ProgressStatus) {
            this.item = item
            when (item.status) {
                PlayCacheStatus.PENDING -> {
                    itemView.btn_status.text = "等待中"
                    itemView.removeCallbacks(downloadRunnable)

                }
                PlayCacheStatus.CACHED -> {
                    itemView.btn_status.text = "下载完成"
                    itemView.removeCallbacks(downloadRunnable)
                }
                PlayCacheStatus.CACHING -> {
                    itemView.btn_status.text = "下载中 ${item.progress}"
                    itemView.postDelayed(downloadRunnable, 1000)
                }
                PlayCacheStatus.ERROR -> {
                    itemView.btn_status.text = "下载错误"
                    itemView.removeCallbacks(downloadRunnable)
                }
                PlayCacheStatus.PAUSED -> {
                    itemView.btn_status.text = "暂停中"
                    itemView.removeCallbacks(downloadRunnable)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, item: ProgressStatus) {
        return holder.setup(item)
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ProgressViewHolder {
        return ProgressViewHolder(parent)
    }
}


class ProgressStatus {
    var status: PlayCacheStatus = PlayCacheStatus.PENDING
        set(value) {
            updateTime = System.currentTimeMillis()
            field = value
        }
    var progress = 0
    var updateTime = 0L
}