package com.swensun.func.coroutines.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.StatusEvent
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.coroutines_fragment.*
import kotlinx.android.synthetic.main.room_fragment.*
import kotlinx.coroutines.cancelAndJoin

class CoroutinesFragment : Fragment() {

    companion object {
        fun newInstance() = CoroutinesFragment()
    }

    private lateinit var viewModel: CoroutinesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.coroutines_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoroutinesViewModel::class.java)
        viewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                StatusEvent.LOADING -> {
                    Logger.d("loading ${it.msg}")
                }
                StatusEvent.SUCCESS -> {
                    Logger.d("SUCCESS")

                }
                StatusEvent.ERROR -> {
                    Logger.d("ERROR ${it.msg}")
                }
            }
        })
        btn_toast.setOnClickListener {
            viewModel.delayToast()
        }
        btn_cancel.setOnClickListener {
            viewModel.cancel()
        }
    }

}
