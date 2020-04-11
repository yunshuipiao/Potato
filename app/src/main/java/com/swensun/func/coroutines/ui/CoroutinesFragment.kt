package com.swensun.func.coroutines.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.swensun.StateEvent
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.coroutines_fragment.*

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
        viewModel = ViewModelProviders.of(this).get(CoroutinesViewModel::class.java)
        viewModel.stateLiveData.observe(this, Observer {
            when (it) {
                StateEvent.LOADING -> {
                    Logger.d("loading ${it.msg}")
                }
                StateEvent.SUCCESS -> {
                    Logger.d("SUCCESS")

                }
                StateEvent.ERROR -> {
                    Logger.d("ERROR ${it.msg}")
                }
            }
        })
        btn_fetch.setOnClickListener {
            viewModel.fetchData()

        }
    }
}
