package com.swensun.func.customview

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swensun.potato.R
import com.swensun.swutils.ui.drawable
import kotlinx.android.synthetic.main.custom_view_fragment.*

class CustomViewFragment : Fragment() {

    companion object {
        fun newInstance() = CustomViewFragment()
    }
    
    var index = 0
    private lateinit var viewModel: CustomViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.custom_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomViewViewModel::class.java)


        showOri()
        tv_bg.setOnClickListener {
            index += 1
            showOri()
        }
    }

    private fun showOri() {
        if (index >= GradientDrawable.Orientation.values().size) {
            index = 0
        }
        GradientDrawable.Orientation.values().getOrNull(index)?.let {
            val bg = drawable{
                radius = 100
                colors = intArrayOf(R.color.red)
            }
            tv_bg.background = bg
        }
    }
}
