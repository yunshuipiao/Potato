package com.swensun.potato


import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.swensun.swutils.util.LogUtils
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
        setTypeFace(rootView, "eng.ttf")
        LogUtils.d("tv size:" + tv1.textSize + " / " + tv2.textSize)
    }


    public fun setTypeFace(view: ViewGroup, font: String) {
        val typeface = Typeface.createFromAsset(view.context.assets, font)
        var count = view.childCount
        (0 until count).forEach {
            var v = view.getChildAt(it)
            if (v is TextView) {
                v.typeface = typeface
            } else if (v is ViewGroup) {
                setTypeFace(v, font)
            }
        }
    }
}


