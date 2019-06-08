package com.swensun.wechat.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swensun.wechat.R
import com.swensun.wechat.repository.proto.UserTweetRes
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    val adapter = Adapter()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initView()
        bindData()
    }

    private fun bindData() {
        viewModel.tweetsLiveData.observe(this, Observer {
            if (it.size <  5) {
                viewModel.hasMoreTweets = false
            }
            adapter.setItemList(it)
        })
        viewModel.requestUserTweetsFirstPage()
    }

    private fun initView() {
        recycler.layoutManager = LinearLayoutManager(requireActivity())
        recycler.adapter = adapter
    }

   inner class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {

        val itemList = arrayListOf<UserTweetRes>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wechat, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position == itemList.size - 2) {
                viewModel.requestUserTweetsNextPage(itemList.size)
            }
            holder.bindData(position)
        }

        fun setItemList(list: List<UserTweetRes>?) {
            if (list == null) {
                itemList.clear()
            } else {
                itemList.addAll(list)
            }
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            val nameView = itemView.findViewById<TextView>(R.id.tv_name)

            fun bindData(position: Int) {
                nameView.text = itemList[position].sender.nick
            }
        }
    }
}




