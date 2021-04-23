package com.swensun.func.touch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swensun.potato.R
import kotlinx.android.synthetic.main.touch_event_fragment.*

class TouchEventFragment : Fragment() {

    companion object {
        fun newInstance() = TouchEventFragment()
    }

    private lateinit var viewModel: TouchEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.touch_event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TouchEventViewModel::class.java)

        touch_red.tag = "red"
        touch_blue.tag = "blue"
        touch_green.tag = "green"
//        touch_one.setOnClickListener {
//            touch_one.log("setOnClickListener")
//        }

//        touch_one.setOnTouchListener { v, event ->
//            touch_one.log("setOnTouchListener. action: ${event?.actionStr}")
//            false
//        }
    }
}