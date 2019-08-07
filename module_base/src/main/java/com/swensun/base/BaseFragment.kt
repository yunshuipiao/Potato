package com.swensun.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * @Date 2019-08-07
 * @author sunwen
 * @Project Potato
 */
abstract class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getContentSubView(), container, false)
    }

    @LayoutRes
    abstract fun getContentSubView(): Int

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()

    }

    abstract fun initView()
}