package com.swensun.func.share.thread

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swensun.potato.R

class ConcurrencyFragment : Fragment() {

    companion object {
        fun newInstance() = ConcurrencyFragment()
    }

    private lateinit var viewModel: ConcurrencyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.concurrency_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConcurrencyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
