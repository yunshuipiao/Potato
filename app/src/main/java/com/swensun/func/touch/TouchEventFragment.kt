package com.swensun.func.touch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swensun.potato.databinding.TouchEventFragmentBinding

class TouchEventFragment : Fragment() {

    companion object {
        fun newInstance() = TouchEventFragment()
    }

    private lateinit var viewModel: TouchEventViewModel
    private lateinit var binding: TouchEventFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = TouchEventFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TouchEventViewModel::class.java)

        binding.touchRed.tag = "red"
        binding.touchBlue.tag = "blue"
        binding.touchGreen.tag = "green"
//        touch_one.setOnClickListener {
//            touch_one.log("setOnClickListener")
//        }


//        touch_one.setOnTouchListener { v, event ->
//            touch_one.log("setOnTouchListener. action: ${event?.actionStr}")
//            false
//        }
    }
}