package com.swensun.potato


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swensun.swutils.ui.adjustTextSize
import com.swensun.swutils.ui.displayMetrics
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.getWinWidth
import kotlinx.android.synthetic.main.fragment_layout.*

class LayoutFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() =
            LayoutFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        val dis = displayMetrics
        val str = "1234567890123456789012345678901234567890123456789012345678901234567890"
        tv_size.text = str
//        tv_size.setOnClickListener {
//            tv_size.post {
//                val width = tv_size.width
//                tv_size.adjustTextSize(width, str)
//            }
//        }
        tv_size1.text = str
        tv_size2.text = str
    }
}


