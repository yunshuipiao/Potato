package com.swensun.func.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.swensun.potato.R

class MemoryFragment : Fragment() {

    companion object {
        fun newInstance() = MemoryFragment()
    }

    private lateinit var viewModel: MemoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.memory_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MemoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
