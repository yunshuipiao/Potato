package com.swensun.potato


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.LinearLayout
import com.swensun.swutils.util.LogUtils
import kotlinx.android.synthetic.main.fragment_touch_event.*
import org.jetbrains.anko.support.v4.toast

class TouchEventFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_touch_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        getMeasureWidth()
    }
    
    companion object {
        @JvmStatic
        fun newInstance() =
            TouchEventFragment()
    }

    override fun onClick(v: View?) {
        toast((v as Button).text)
    }

    fun getMeasureWidth() {
        activity?.window?.decorView?.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY)
        val width = cus_view2.measuredWidth
        LogUtils.d("width: $width")
    }
}
