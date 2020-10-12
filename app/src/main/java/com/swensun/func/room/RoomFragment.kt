package com.swensun.func.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swensun.potato.R
import kotlinx.android.synthetic.main.room_fragment.*

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        initView()
    }

    private fun initView() {
        btn_clear.setOnClickListener {
            viewModel.clearAllData()
        }
        btn_query.setOnClickListener {
            viewModel.queryRoom()
        }
        btn_add.setOnClickListener {
            viewModel.addEntity()
        }
        btn_migration.setOnClickListener {
            viewModel.migration()
        }
    }
}
