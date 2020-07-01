package com.swensun.func.status

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swensun.potato.R

class StatusBarFragment : Fragment() {

    companion object {
        fun newInstance() = StatusBarFragment()
    }

    private lateinit var viewModel: StatusBarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.status_bar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StatusBarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}