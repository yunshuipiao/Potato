package com.swensun.potato.frag

import android.os.Bundle
import android.text.format.DateFormat
import com.swensun.base.BaseFragment
import com.swensun.potato.R
import kotlinx.android.synthetic.main.dialog_score.view.*
import kotlinx.android.synthetic.main.fragment_time.*
import java.text.Format

/**
 * @Date 2019-08-07
 * @author sunwen
 * @Project Potato
 */
class TimeFragment: BaseFragment() {

    companion object {
        fun newInstance() = TimeFragment()
    }
    
    override fun getContentSubView(): Int {
        return R.layout.fragment_time
    }

    override fun initView() {
        ft_tv_time.text = getCurTime()
    }
}


fun getCurTime(): String {
    val now = System.currentTimeMillis()
    return DateFormat.format("hh:mm:ss", now).toString()
}