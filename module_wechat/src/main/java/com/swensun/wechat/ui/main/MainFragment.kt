package com.swensun.wechat.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swensun.wechat.R
import kotlinx.android.synthetic.main.main_fragment.*
import java.lang.Exception

class MainFragment : Fragment() {

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
    }

    private fun initView() {
        recycler.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = Adapter()
        val list = arrayListOf<Int>()
        (0..99).forEach {
            list.add(it)
        }
        adapter.setItemList(list)
        recycler.adapter = adapter
    }
}

class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {

    val itemList = arrayListOf<Int>()
    val viewHolderSet = hashSetOf<ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wechat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewHolderSet.add(holder)
//        Log.d("TAG", "viewHolder size: ${viewHolderSet.size}")
        holder.bindData(position)
    }

    fun setItemList(list: ArrayList<Int>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nameView = itemView.findViewById<TextView>(R.id.tv_name)

        fun bindData(position: Int) {
            nameView.text = itemList[position].toString()
        }
    }

}


