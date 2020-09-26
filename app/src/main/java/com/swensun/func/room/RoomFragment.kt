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
